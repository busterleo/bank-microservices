package com.bank.account.application.usecase;

import com.bank.account.domain.model.Account;
import com.bank.account.domain.ports.input.UpdateCustomerUseCase;
import com.bank.account.domain.ports.output.AccountRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class UpdateAccountUseCaseImpl implements UpdateCustomerUseCase {
  public final AccountRepositoryPort accountRepositoryPort;

  @Override
  public Mono<Account> update(String customerId, Account customer) {
    return null;
  }
}
