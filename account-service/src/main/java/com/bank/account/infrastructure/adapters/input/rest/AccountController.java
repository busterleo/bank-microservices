package com.bank.account.infrastructure.adapters.input.rest;

import com.bank.account.domain.ports.input.CreateAccountUseCase;
import com.bank.account.domain.ports.input.DisableAccountUseCase;
import com.bank.account.domain.ports.input.FindAccountUseCase;
import com.bank.account.infrastructure.adapters.input.rest.mapper.AccountRestMapper;
import com.bank.account.infrastructure.web.api.AccountsApi;
import com.bank.account.infrastructure.web.dto.AccountRequest;
import com.bank.account.infrastructure.web.dto.AccountResponse;
import com.bank.account.infrastructure.web.dto.AccountStatus;
import com.bank.account.infrastructure.web.dto.BalanceResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class AccountController implements AccountsApi {

  private final CreateAccountUseCase createAccountUseCase;
  private final FindAccountUseCase findAccountUseCase;
  private final DisableAccountUseCase disableAccountUseCase;
  private final AccountRestMapper mapper;
  private static final Logger LOG = LoggerFactory.getLogger(AccountController.class);

  @Override
  public Mono<ResponseEntity<AccountResponse>> createAccount(
      Mono<AccountRequest> accountRequest, ServerWebExchange exchange) {

    LOG.info("Creating account");

    return accountRequest
        .map(mapper::toDomain)
        .flatMap(createAccountUseCase::create)
        .map(mapper::toResponse)
        .map(response -> ResponseEntity.status(HttpStatus.CREATED).body(response));
  }

  @Override
  public Mono<ResponseEntity<AccountResponse>> findAccountById(
      String accountId, ServerWebExchange exchange) {

    LOG.info("Finding account by id {}", accountId);

    return findAccountUseCase.findById(accountId).map(mapper::toResponse).map(ResponseEntity::ok);
  }

  @Override
  public Mono<ResponseEntity<Flux<AccountResponse>>> findAccounts(
      String customerId, AccountStatus status, ServerWebExchange exchange) {

    LOG.info("Finding accounts customerId={} status={}", customerId, status);

    return findAccountUseCase
        .findByCustomer(customerId, mapper.toDomain(status))
        .map(mapper::toResponse)
        .collectList()
        .map(list -> ResponseEntity.ok(Flux.fromIterable(list)));
  }

  @Override
  public Mono<ResponseEntity<BalanceResponse>> getBalance(
      String accountId, ServerWebExchange exchange) {

    LOG.info("Getting balance accountId={}", accountId);

    return findAccountUseCase
        .getBalance(accountId)
        .map(mapper::toBalanceResponse)
        .map(ResponseEntity::ok);
  }

  @Override
  public Mono<ResponseEntity<Void>> disableAccount(String accountId, ServerWebExchange exchange) {

    LOG.info("Disabling account {}", accountId);

    return disableAccountUseCase
        .disable(accountId)
        .then(Mono.just(ResponseEntity.noContent().build()));
  }
}
