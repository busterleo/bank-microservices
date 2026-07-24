package com.bank.account.domain.ports.input;

import com.bank.account.domain.enums.AccountStatus;
import com.bank.account.domain.model.Account;
import com.bank.account.domain.model.AccountBalance;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface FindAccountUseCase {
  Mono<Account> findById(String accountId);

  Flux<Account> findByCustomer(String customerId, AccountStatus status);

  Mono<AccountBalance> getBalance(String accountId);
}
