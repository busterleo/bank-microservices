package com.bank.account.domain.ports.input;

import com.bank.account.domain.model.Account;
import reactor.core.publisher.Mono;

public interface UpdateCustomerUseCase {

  Mono<Account> update(String customerId, Account customer);
}
