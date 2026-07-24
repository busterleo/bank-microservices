package com.bank.credit.infrastructure.adapters.output.persistence.repository;

import com.bank.credit.infrastructure.entity.CreditDocument;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface CreditReactiveRepository extends ReactiveMongoRepository<CreditDocument, String> {
  Flux<CreditDocument> findByCustomerId(String customerId);
}
