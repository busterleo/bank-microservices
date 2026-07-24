package com.bank.credit.infrastructure.adapters.input.rest;

import com.bank.credit.domain.ports.input.CreateCreditUseCase;
import com.bank.credit.domain.ports.input.FindCreditUseCase;
import com.bank.credit.domain.ports.input.FindProductTransactionsUseCase;
import com.bank.credit.domain.ports.input.GetProductBalanceUseCase;
import com.bank.credit.domain.ports.input.PayCreditUseCase;
import com.bank.credit.infrastructure.adapters.input.rest.mapper.CreditRestMapper;
import com.bank.credit.infrastructure.web.api.CreditsApi;
import com.bank.credit.infrastructure.web.dto.BalanceResponse;
import com.bank.credit.infrastructure.web.dto.CreditRequest;
import com.bank.credit.infrastructure.web.dto.CreditResponse;
import com.bank.credit.infrastructure.web.dto.CreditStatus;
import com.bank.credit.infrastructure.web.dto.CreditType;
import com.bank.credit.infrastructure.web.dto.PaymentRequest;
import com.bank.credit.infrastructure.web.dto.PaymentResponse;
import com.bank.credit.infrastructure.web.dto.TransactionResponse;
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
public class CreditController implements CreditsApi {
  private static final Logger LOG = LoggerFactory.getLogger(CreditController.class);
  private final CreateCreditUseCase createCreditUseCase;
  private final FindCreditUseCase findCreditUseCase;
  private final PayCreditUseCase payCreditUseCase;
  private final GetProductBalanceUseCase getProductBalanceUseCase;
  private final FindProductTransactionsUseCase findProductTransactionsUseCase;
  private final CreditRestMapper mapper;

  @Override
  public Mono<ResponseEntity<CreditResponse>> createCredit(
      Mono<CreditRequest> creditRequest, ServerWebExchange exchange) {

    LOG.info("Creating credit");

    return creditRequest
        .map(mapper::toDomain)
        .flatMap(createCreditUseCase::create)
        .map(mapper::toResponse)
        .map(response -> ResponseEntity.status(HttpStatus.CREATED).body(response));
  }

  @Override
  public Mono<ResponseEntity<CreditResponse>> findCreditById(
      String creditId, ServerWebExchange exchange) {

    LOG.info("Finding credit id={}", creditId);

    return findCreditUseCase.findById(creditId).map(mapper::toResponse).map(ResponseEntity::ok);
  }

  @Override
  public Mono<ResponseEntity<Flux<CreditResponse>>> findCredits(
      String customerId, CreditType type, CreditStatus status, ServerWebExchange exchange) {

    LOG.info("Finding credits customerId={} type={} status={}", customerId, type, status);

    return Mono.just(
        ResponseEntity.ok(
            findCreditUseCase
                .findByCustomer(customerId, mapper.toDomain(type), mapper.toDomain(status))
                .map(mapper::toResponse)));
  }

  @Override
  public Mono<ResponseEntity<PaymentResponse>> payCredit(
      String creditId, Mono<PaymentRequest> paymentRequest, ServerWebExchange exchange) {

    LOG.info("Paying credit id={}", creditId);

    return paymentRequest
        .flatMap(request -> payCreditUseCase.pay(creditId, request.getAmount()))
        .map(mapper::toPaymentResponse)
        .map(response -> ResponseEntity.status(HttpStatus.CREATED).body(response));
  }

  @Override
  public Mono<ResponseEntity<BalanceResponse>> getBalance(
      String productId, ServerWebExchange exchange) {

    LOG.info("Getting balance productId={}", productId);

    return getProductBalanceUseCase
        .getBalance(productId)
        .map(mapper::toBalanceResponse)
        .map(ResponseEntity::ok);
  }

  @Override
  public Mono<ResponseEntity<Flux<TransactionResponse>>> findTransactions(
      String productId, ServerWebExchange exchange) {

    LOG.info("Finding transactions productId={}", productId);

    return Mono.just(
        ResponseEntity.ok(
            findProductTransactionsUseCase
                .getTransactions(productId)
                .map(mapper::toTransactionResponse)));
  }
}
