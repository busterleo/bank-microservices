package com.bank.customer.application.usecase;

import com.bank.customer.domain.exception.CustomerAlreadyExistsException;
import com.bank.customer.domain.model.Customer;
import com.bank.customer.domain.ports.input.CreateCustomerUseCase;
import com.bank.customer.domain.ports.output.CustomerRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class CreateCustomerUseCaseImpl implements CreateCustomerUseCase {

  public final CustomerRepositoryPort customerRepositoryPort;
  private static final Logger LOG = LoggerFactory.getLogger(CreateCustomerUseCaseImpl.class);

  @Override
  public Mono<Customer> create(Customer customer) {
    LOG.info("Creating customer with documentNumber={}", customer.getDocumentNumber());
    return customerRepositoryPort
        .existsByDocumentNumber(customer.getDocumentNumber())
        .flatMap(
            exists -> {
              if (exists) {
                return Mono.error(new CustomerAlreadyExistsException(customer.getDocumentNumber()));
              }
              customer.initializeCreation();
              return customerRepositoryPort.save(customer);
            });
  }
}
