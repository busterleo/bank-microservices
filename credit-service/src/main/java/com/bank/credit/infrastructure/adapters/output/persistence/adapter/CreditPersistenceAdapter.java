package com.bank.credit.infrastructure.adapters.output.persistence.adapter;

import com.bank.credit.domain.enums.CreditStatus;
import com.bank.credit.domain.enums.CreditType;
import com.bank.credit.domain.exception.CreditNotFoundException;
import com.bank.credit.domain.exception.CustomerNotFoundException;
import com.bank.credit.domain.model.Balance;
import com.bank.credit.domain.model.Credit;
import com.bank.credit.domain.model.Transaction;
import com.bank.credit.domain.ports.output.CreditRepositoryPort;
import com.bank.credit.infrastructure.adapters.output.persistence.mapper.CreditPersistenceMapper;
import com.bank.credit.infrastructure.adapters.output.persistence.repository.CreditReactiveRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class CreditPersistenceAdapter implements CreditRepositoryPort {

  private final CreditReactiveRepository repository;
  private final CreditPersistenceMapper mapper;
  private static final Logger LOG = LoggerFactory.getLogger(CreditPersistenceAdapter.class);

  @Override
  public Mono<Credit> save(Credit credit) {

    LOG.info("Saving credit");

    return repository.save(mapper.toDocument(credit)).map(mapper::toDomain);
  }

  @Override
  public Mono<Credit> findById(String id) {

    LOG.info("Finding credit id={}", id);

    return repository
        .findById(id)
        .switchIfEmpty(Mono.error(new CreditNotFoundException(id)))
        .map(mapper::toDomain);
  }

  @Override
  public Flux<Credit> findByCustomer(String customerId, CreditType type, CreditStatus status) {

    LOG.info("Finding credits customerId={} type={} status={}", customerId, type, status);

    return repository
        .findByCustomerId(customerId)
        .switchIfEmpty(Mono.error(new CustomerNotFoundException(customerId)))
        .map(mapper::toDomain)
        .filter(credit -> type == null || credit.getType() == type)
        .filter(credit -> status == null || credit.getStatus() == status);
  }

  @Override
  public Mono<Balance> getBalance(String productId) {

    LOG.info("Getting balance productId={}", productId);
    return repository
        .findById(productId)
        .switchIfEmpty(Mono.error(new CreditNotFoundException(productId)))
        .map(
            document ->
                Balance.builder()
                    .productId(document.getId())
                    .balance(document.getBalance())
                    .availableBalance(document.getBalance())
                    .build());
  }

  @Override
  public Flux<Transaction> getTransactions(String productId) {

    LOG.info("Getting transactions productId={}", productId);
    return repository
        .findById(productId)
        .flatMapMany(document -> Flux.fromIterable(document.getTransactions()))
        .map(mapper::toDomain);
  }
}
