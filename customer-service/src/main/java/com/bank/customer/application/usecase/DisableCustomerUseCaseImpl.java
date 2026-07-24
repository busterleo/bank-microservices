package com.bank.customer.application.usecase;

import com.bank.customer.domain.exception.CustomerNotFoundException;
import com.bank.customer.domain.ports.input.DisableCustomerUseCase;
import com.bank.customer.domain.ports.output.CustomerRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class DisableCustomerUseCaseImpl implements DisableCustomerUseCase {

  private final CustomerRepositoryPort customerRepositoryPort;

  @Override
  public Mono<Void> disable(String customerId) {
    return customerRepositoryPort
        .findById(customerId)
        .switchIfEmpty(Mono.error(new CustomerNotFoundException(customerId)))
        .flatMap(
            customer -> {
              customer.disable();
              return customerRepositoryPort.save(customer);
            })
        .then();
  }
}
