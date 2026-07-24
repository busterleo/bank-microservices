package com.bank.customer.domain.ports.input;

import com.bank.customer.domain.model.Customer;
import reactor.core.publisher.Mono;

public interface CreateCustomerUseCase {

  Mono<Customer> create(Customer customer);
}
