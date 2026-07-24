package com.bank.credit.domain.ports.input;

import com.bank.credit.domain.model.Credit;
import reactor.core.publisher.Mono;

public interface CreateCreditUseCase {
  Mono<Credit> create(Credit credit);
}
