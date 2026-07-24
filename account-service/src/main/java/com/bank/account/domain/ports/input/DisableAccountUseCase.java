package com.bank.account.domain.ports.input;

import reactor.core.publisher.Mono;

public interface DisableAccountUseCase {

  Mono<Void> disable(String accountId);
}
