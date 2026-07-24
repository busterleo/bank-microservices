package com.bank.account.application.usecase;

import com.bank.account.domain.enums.AccountStatus;
import com.bank.account.domain.exception.AccountNotFoundException;
import com.bank.account.domain.model.Account;
import com.bank.account.domain.model.AccountBalance;
import com.bank.account.domain.ports.input.FindAccountUseCase;
import com.bank.account.domain.ports.output.AccountRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class FindAccountUseCaseImpl implements FindAccountUseCase {

  private final AccountRepositoryPort accountRepositoryPort;
  private static final Logger LOG = LoggerFactory.getLogger(FindAccountUseCaseImpl.class);

  @Override
  public Mono<Account> findById(String accountId) {

    LOG.info("Finding account by id {}", accountId);
    return accountRepositoryPort
        .findById(accountId)
        .switchIfEmpty(Mono.error(new AccountNotFoundException(accountId)));
  }

  @Override
  public Flux<Account> findByCustomer(String customerId, AccountStatus status) {

    LOG.info("Finding accounts for customerId={} with status={}", customerId, status);

    return accountRepositoryPort.findByCustomerId(customerId, status);
  }

  @Override
  public Mono<AccountBalance> getBalance(String accountId) {

    LOG.info("Getting balance for account {}", accountId);

    return accountRepositoryPort
        .findBalanceByAccountId(accountId)
        .switchIfEmpty(Mono.error(new AccountNotFoundException(accountId)));
  }
}
