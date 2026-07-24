package com.bank.credit.application.usecase;

import com.bank.credit.domain.enums.CreditStatus;
import com.bank.credit.domain.enums.CreditType;
import com.bank.credit.domain.model.Credit;
import com.bank.credit.domain.ports.input.FindCreditUseCase;
import com.bank.credit.domain.ports.output.CreditRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class FindCreditUseCaseImpl implements FindCreditUseCase {

  private final CreditRepositoryPort creditRepositoryPort;

  @Override
  public Mono<Credit> findById(String creditId) {
    return creditRepositoryPort.findById(creditId);
  }

  @Override
  public Flux<Credit> findByCustomer(String customerId, CreditType type, CreditStatus status) {
    return creditRepositoryPort.findByCustomer(customerId, type, status);
  }
}
