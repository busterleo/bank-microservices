package com.bank.customer.infrastructure.adapters.input.rest;

import com.bank.customer.domain.ports.input.CreateCustomerUseCase;
import com.bank.customer.domain.ports.input.DisableCustomerUseCase;
import com.bank.customer.domain.ports.input.FindCustomerUseCase;
import com.bank.customer.domain.ports.input.UpdateCustomerUseCase;
import com.bank.customer.infrastructure.adapters.input.rest.mapper.CustomerRestMapper;
import com.bank.customer.infrastructure.web.api.CustomersApi;
import com.bank.customer.infrastructure.web.dto.CustomerPageResponse;
import com.bank.customer.infrastructure.web.dto.CustomerRequest;
import com.bank.customer.infrastructure.web.dto.CustomerResponse;
import com.bank.customer.infrastructure.web.dto.CustomerUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class CustomerController implements CustomersApi {

  private final CreateCustomerUseCase createCustomerUseCase;
  private final FindCustomerUseCase findCustomerUseCase;
  private final UpdateCustomerUseCase updateCustomerUseCase;
  private final DisableCustomerUseCase disableCustomerUseCase;
  private final CustomerRestMapper mapper;
  private static final Logger LOG = LoggerFactory.getLogger(CustomerController.class);

  @Override
  public Mono<ResponseEntity<CustomerResponse>> createCustomer(
      Mono<CustomerRequest> customerRequest, ServerWebExchange exchange) {

    LOG.info("Creating customer");
    return customerRequest
        .map(mapper::toDomain)
        .flatMap(createCustomerUseCase::create)
        .map(mapper::toResponse)
        .map(response -> ResponseEntity.status(HttpStatus.CREATED).body(response));
  }

  @Override
  public Mono<ResponseEntity<CustomerResponse>> findCustomerById(
      String customerId, ServerWebExchange exchange) {
    LOG.info("Finding customer by id: {}", customerId);
    return findCustomerUseCase.findById(customerId).map(mapper::toResponse).map(ResponseEntity::ok);
  }

  @Override
  public Mono<ResponseEntity<CustomerResponse>> findCustomerByDocument(
      String documentNumber, ServerWebExchange exchange) {
    LOG.info("Finding customer by document");
    return findCustomerUseCase
        .findByDocumentNumber(documentNumber)
        .map(mapper::toResponse)
        .map(ResponseEntity::ok);
  }

  @Override
  public Mono<ResponseEntity<CustomerPageResponse>> findCustomers(
      com.bank.customer.infrastructure.web.dto.CustomerType customerType,
      com.bank.customer.infrastructure.web.dto.CustomerStatus status,
      String documentNumber,
      Integer page,
      Integer size,
      ServerWebExchange exchange) {
    LOG.info("Searching customers page={}, size={}", page, size);
    return findCustomerUseCase
        .findAll(mapper.toDomain(customerType), mapper.toDomain(status), documentNumber, page, size)
        .map(mapper::toPageResponse)
        .map(ResponseEntity::ok);
  }

  @Override
  public Mono<ResponseEntity<CustomerResponse>> updateCustomer(
      String customerId, Mono<CustomerUpdateRequest> customerRequest, ServerWebExchange exchange) {

    LOG.info("Updating customer {}", customerId);
    return customerRequest
        .map(mapper::toDomainUpdate)
        .flatMap(customer -> updateCustomerUseCase.update(customerId, customer))
        .map(mapper::toResponse)
        .map(ResponseEntity::ok);
  }

  @Override
  public Mono<ResponseEntity<Void>> disableCustomer(String customerId, ServerWebExchange exchange) {

    LOG.info("Disabling customer {}", customerId);
    return disableCustomerUseCase
        .disable(customerId)
        .thenReturn(ResponseEntity.noContent().build());
  }
}
