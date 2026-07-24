package com.bank.customer.domain.ports.input;

import reactor.core.publisher.Mono;

public interface DisableCustomerUseCase {
  Mono<Void> disable(String customerId);
}
