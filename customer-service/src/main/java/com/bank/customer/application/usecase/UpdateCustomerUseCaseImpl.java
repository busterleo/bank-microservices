package com.bank.customer.application.usecase;

import com.bank.customer.domain.exception.CustomerNotFoundException;
import com.bank.customer.domain.model.Customer;
import com.bank.customer.domain.ports.input.UpdateCustomerUseCase;
import com.bank.customer.domain.ports.output.CustomerRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class UpdateCustomerUseCaseImpl implements UpdateCustomerUseCase {
  public final CustomerRepositoryPort customerRepositoryPort;

  @Override
  public Mono<Customer> update(String customerId, Customer customer) {
    return customerRepositoryPort
        .findById(customerId)
        .switchIfEmpty(Mono.error(new CustomerNotFoundException(customerId)))
        .flatMap(
            existingCustomer -> {
              existingCustomer.updateData(customer);
              return customerRepositoryPort.update(existingCustomer);
            });
  }
}
