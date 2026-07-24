package com.bank.customer.infrastructure.adapters.output.persistence.adapter;

import com.bank.customer.domain.enums.CustomerStatus;
import com.bank.customer.domain.enums.CustomerType;
import com.bank.customer.domain.model.Customer;
import com.bank.customer.domain.model.CustomerPage;
import com.bank.customer.domain.ports.output.CustomerRepositoryPort;
import com.bank.customer.infrastructure.adapters.output.persistence.mapper.CustomerPersistenceMapper;
import com.bank.customer.infrastructure.adapters.output.persistence.repository.CustomerReactiveRepository;
import com.bank.customer.infrastructure.entity.CustomerDocument;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class CustomerPersistenceAdapter implements CustomerRepositoryPort {

  private final CustomerReactiveRepository repository;
  private final ReactiveMongoTemplate mongoTemplate;
  private final CustomerPersistenceMapper mapper;
  private static final Logger LOG = LoggerFactory.getLogger(CustomerPersistenceAdapter.class);

  @Override
  public Mono<Customer> save(Customer customer) {

    LOG.info("Saving customer with documentNumber={}", customer.getDocumentNumber());
    CustomerDocument document = mapper.toDocument(customer);
    return repository.save(document).map(mapper::toDomain);
  }

  @Override
  public Mono<Boolean> existsByDocumentNumber(String documentNumber) {
    LOG.debug("Checking customer existence by documentNumber={}", documentNumber);
    return repository.existsByDocumentNumber(documentNumber);
  }

  @Override
  public Mono<Customer> findById(String customerId) {

    LOG.debug("Finding customer by id={}", customerId);

    return repository.findById(customerId).map(mapper::toDomain);
  }

  @Override
  public Mono<Customer> findByDocumentNumber(String documentNumber) {

    LOG.debug("Finding customer by documentNumber={}", documentNumber);

    return repository.findByDocumentNumber(documentNumber).map(mapper::toDomain);
  }

  @Override
  public Mono<CustomerPage> findAll(
      CustomerType customerType,
      CustomerStatus status,
      String documentNumber,
      Integer page,
      Integer size) {

    Query query = new Query();

    if (customerType != null) {
      query.addCriteria(Criteria.where("customerType").is(customerType));
    }

    if (status != null) {
      query.addCriteria(Criteria.where("status").is(status));
    }

    if (documentNumber != null && !documentNumber.isBlank()) {
      query.addCriteria(Criteria.where("documentNumber").is(documentNumber));
    }

    Query countQuery = Query.of(query);

    query.with(PageRequest.of(page, size));

    Mono<Long> totalElements = mongoTemplate.count(countQuery, CustomerDocument.class);

    Mono<List<Customer>> customers =
        mongoTemplate.find(query, CustomerDocument.class).map(mapper::toDomain).collectList();

    return Mono.zip(customers, totalElements)
        .map(
            tuple -> {
              List<Customer> content = tuple.getT1();
              long total = tuple.getT2();

              int totalPages = (int) Math.ceil((double) total / size);

              return CustomerPage.builder()
                  .content(content)
                  .page(page)
                  .size(size)
                  .totalElements(total)
                  .totalPages(totalPages)
                  .build();
            });
  }

  @Override
  public Mono<Customer> update(Customer customer) {

    LOG.info("Updating customer id={}", customer.getId());
    return repository.save(mapper.toDocument(customer)).map(mapper::toDomain);
  }
}
