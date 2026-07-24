package com.bank.credit.application.usecase;

import com.bank.credit.domain.enums.CreditStatus;
import com.bank.credit.domain.exception.CreditNotFoundException;
import com.bank.credit.domain.model.Payment;
import com.bank.credit.domain.ports.input.PayCreditUseCase;
import com.bank.credit.domain.ports.output.CreditRepositoryPort;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class PayCreditUseCaseImpl implements PayCreditUseCase {

  private final CreditRepositoryPort creditRepositoryPort;

  @Override
  public Mono<Payment> pay(String creditId, BigDecimal amount) {

    return creditRepositoryPort
        .findById(creditId)
        .switchIfEmpty(Mono.error(new CreditNotFoundException("Credit not found")))
        .flatMap(
            credit -> {
              credit.setBalance(credit.getBalance().subtract(amount));

              if (credit.getBalance().compareTo(BigDecimal.ZERO) <= 0) {
                credit.setBalance(BigDecimal.ZERO);
                credit.setStatus(CreditStatus.PAID);
              }
              return creditRepositoryPort
                  .save(credit)
                  .thenReturn(
                      Payment.builder()
                          .id(UUID.randomUUID().toString())
                          .amount(amount)
                          .createdAt(LocalDateTime.now())
                          .build());
            });
  }
}
