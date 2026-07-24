package com.bank.customer.infrastructure.adapters.output.persistence.repository;

import com.bank.customer.infrastructure.entity.CustomerDocument;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface CustomerReactiveRepository
    extends ReactiveMongoRepository<CustomerDocument, String> {
  Mono<CustomerDocument> findByDocumentNumber(String documentNumber);

  Mono<Boolean> existsByDocumentNumber(String documentNumber);
}
