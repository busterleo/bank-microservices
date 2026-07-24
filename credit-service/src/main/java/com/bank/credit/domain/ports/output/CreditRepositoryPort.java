package com.bank.credit.domain.ports.output;

import com.bank.credit.domain.enums.CreditStatus;
import com.bank.credit.domain.enums.CreditType;
import com.bank.credit.domain.model.Balance;
import com.bank.credit.domain.model.Credit;
import com.bank.credit.domain.model.Transaction;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CreditRepositoryPort {
  Mono<Credit> save(Credit credit);

  Mono<Credit> findById(String id);

  Flux<Credit> findByCustomer(String customerId, CreditType type, CreditStatus status);

  Mono<Balance> getBalance(String productId);

  Flux<Transaction> getTransactions(String productId);
}
