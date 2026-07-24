package com.bank.credit.application.usecase;

import com.bank.credit.domain.model.Transaction;
import com.bank.credit.domain.ports.input.FindProductTransactionsUseCase;
import com.bank.credit.domain.ports.output.CreditRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Component
@RequiredArgsConstructor
public class FindProductTransactionsUseCaseImpl implements FindProductTransactionsUseCase {

  public final CreditRepositoryPort creditRepositoryPort;

  @Override
  public Flux<Transaction> getTransactions(String productId) {

    return creditRepositoryPort.getTransactions(productId);
  }
}
