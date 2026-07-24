package com.bank.customer.application.usecase;

import com.bank.customer.domain.exception.CustomerNotFoundException;
import com.bank.customer.domain.model.Customer;
import com.bank.customer.domain.model.CustomerPage;
import com.bank.customer.domain.ports.input.FindCustomerUseCase;
import com.bank.customer.domain.ports.output.CustomerRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class FindCustomerUseCaseImpl implements FindCustomerUseCase {

  public final CustomerRepositoryPort customerRepositoryPort;

  @Override
  public Mono<Customer> findById(String customerId) {
    return customerRepositoryPort
        .findById(customerId)
        .switchIfEmpty(Mono.error(new CustomerNotFoundException(customerId)));
  }

  @Override
  public Mono<Customer> findByDocumentNumber(String documentNumber) {
    return customerRepositoryPort
        .findByDocumentNumber(documentNumber)
        .switchIfEmpty(
            Mono.error(new CustomerNotFoundException("document number", documentNumber)));
  }

  @Override
  public Mono<CustomerPage> findAll(
      com.bank.customer.domain.enums.CustomerType customerType,
      com.bank.customer.domain.enums.CustomerStatus status,
      String documentNumber,
      Integer page,
      Integer size) {
    return customerRepositoryPort.findAll(customerType, status, documentNumber, page, size);
  }
}
