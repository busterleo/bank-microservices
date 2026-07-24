package com.bank.account.domain.ports.output;

import com.bank.account.domain.enums.AccountStatus;
import com.bank.account.domain.model.Account;
import com.bank.account.domain.model.AccountBalance;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AccountRepositoryPort {

  Mono<Account> save(Account account);

  Mono<Account> findById(String accountId);

  Flux<Account> findByCustomerId(String customerId, AccountStatus status);

  Mono<AccountBalance> findBalanceByAccountId(String accountId);
}
