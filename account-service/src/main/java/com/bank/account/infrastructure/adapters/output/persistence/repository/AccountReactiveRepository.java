package com.bank.account.infrastructure.adapters.output.persistence.repository;

import com.bank.account.domain.enums.AccountStatus;
import com.bank.account.infrastructure.entity.AccountDocument;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AccountReactiveRepository
    extends ReactiveMongoRepository<AccountDocument, String> {
  Flux<AccountDocument> findByCustomerId(String customerId);

  Flux<AccountDocument> findByCustomerIdAndStatus(String customerId, AccountStatus status);

  Mono<Boolean> existsByAccountNumber(String accountNumber);
}
