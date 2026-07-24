package com.bank.customer.domain.ports.output;

import com.bank.customer.domain.enums.CustomerStatus;
import com.bank.customer.domain.enums.CustomerType;
import com.bank.customer.domain.model.Customer;
import com.bank.customer.domain.model.CustomerPage;
import reactor.core.publisher.Mono;

public interface CustomerRepositoryPort {

  Mono<Customer> save(Customer customer);

  Mono<Boolean> existsByDocumentNumber(String documentNumber);

  Mono<Customer> findById(String customerId);

  Mono<Customer> findByDocumentNumber(String documentNumber);

  Mono<CustomerPage> findAll(
      CustomerType customerType,
      CustomerStatus status,
      String documentNumber,
      Integer page,
      Integer size);

  Mono<Customer> update(Customer customer);
}
