package com.bank.account.domain.ports.input;

import com.bank.account.domain.model.Account;
import reactor.core.publisher.Mono;

public interface CreateAccountUseCase {

  Mono<Account> create(Account account);
}
