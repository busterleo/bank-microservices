package com.bank.account.infrastructure.adapters.output.persistence.adapter;

import com.bank.account.domain.enums.AccountStatus;
import com.bank.account.domain.model.Account;
import com.bank.account.domain.model.AccountBalance;
import com.bank.account.domain.ports.output.AccountRepositoryPort;
import com.bank.account.infrastructure.adapters.output.persistence.mapper.AccountBalanceMapper;
import com.bank.account.infrastructure.adapters.output.persistence.mapper.AccountPersistenceMapper;
import com.bank.account.infrastructure.adapters.output.persistence.repository.AccountReactiveRepository;
import com.bank.account.infrastructure.entity.AccountDocument;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class AccountPersistenceAdapter implements AccountRepositoryPort {

  private final AccountReactiveRepository repository;
  private final AccountBalanceMapper balanceMapper;
  private final AccountPersistenceMapper mapper;

  @Override
  public Mono<Account> save(Account account) {
    AccountDocument document = mapper.toDocument(account);
    return repository.save(document).map(mapper::toDomain);
  }

  @Override
  public Mono<Account> findById(String accountId) {
    return repository.findById(accountId).map(mapper::toDomain);
  }

  @Override
  public Flux<Account> findByCustomerId(String customerId, AccountStatus status) {
    Flux<AccountDocument> result;
    if (status != null) {
      result = repository.findByCustomerIdAndStatus(customerId, status);
    } else {
      result = repository.findByCustomerId(customerId);
    }
    return result.map(mapper::toDomain);
  }

  @Override
  public Mono<AccountBalance> findBalanceByAccountId(String accountId) {
    return repository.findById(accountId).map(balanceMapper::toDomain);
  }
}
