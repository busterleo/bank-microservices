package com.bank.credit.application.usecase;

import com.bank.credit.domain.model.Balance;
import com.bank.credit.domain.ports.input.GetProductBalanceUseCase;
import com.bank.credit.domain.ports.output.CreditRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class GetProductBalanceUseCaseImpl implements GetProductBalanceUseCase {

  public final CreditRepositoryPort customerRepositoryPort;

  @Override
  public Mono<Balance> getBalance(String productId) {

    return customerRepositoryPort.getBalance(productId);
  }
}
