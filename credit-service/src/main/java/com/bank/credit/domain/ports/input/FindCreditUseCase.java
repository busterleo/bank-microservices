package com.bank.credit.domain.ports.input;

import com.bank.credit.domain.enums.CreditStatus;
import com.bank.credit.domain.enums.CreditType;
import com.bank.credit.domain.model.Credit;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface FindCreditUseCase {
  Mono<Credit> findById(String creditId);

  Flux<Credit> findByCustomer(String customerId, CreditType type, CreditStatus status);
}
