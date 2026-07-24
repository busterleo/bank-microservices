package com.bank.account.application.usecase;

import com.bank.account.domain.exception.AccountNotFoundException;
import com.bank.account.domain.ports.input.DisableAccountUseCase;
import com.bank.account.domain.ports.output.AccountRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class DisableAccountUseCaseImpl implements DisableAccountUseCase {

  private final AccountRepositoryPort accountRepositoryPort;

  @Override
  public Mono<Void> disable(String accountId) {
    return accountRepositoryPort
        .findById(accountId)
        .switchIfEmpty(Mono.error(new AccountNotFoundException(accountId)))
        .flatMap(
            account -> {
              account.disable();
              return accountRepositoryPort.save(account);
            })
        .then();
  }
}
