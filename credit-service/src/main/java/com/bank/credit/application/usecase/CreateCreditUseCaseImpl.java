package com.bank.credit.application.usecase;

import com.bank.credit.domain.enums.CreditStatus;
import com.bank.credit.domain.exception.CustomerNotFoundException;
import com.bank.credit.domain.model.Credit;
import com.bank.credit.domain.ports.input.CreateCreditUseCase;
import com.bank.credit.domain.ports.output.CreditRepositoryPort;
import com.bank.credit.domain.validation.CreditValidator;
import com.bank.credit.infrastructure.adapters.output.webclient.exception.CustomerClientException;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class CreateCreditUseCaseImpl implements CreateCreditUseCase {

  public final CreditRepositoryPort creditRepositoryPort;
  private final CreditValidator creditValidator;
  private static final Logger LOG = LoggerFactory.getLogger(CreateCreditUseCaseImpl.class);

  @Override
  public Mono<Credit> create(Credit credit) {

    return creditValidator
        .validate(credit)
        .then(saveCredit(credit))
        .onErrorResume(
            CustomerClientException.class,
            ex -> Mono.error(new CustomerNotFoundException(credit.getCustomerId())));
  }

  private Mono<Credit> saveCredit(Credit credit) {

    credit.setId(UUID.randomUUID().toString());
    credit.setBalance(credit.getAmount());
    credit.setStatus(CreditStatus.ACTIVE);
    credit.setCreatedAt(LocalDateTime.now());
    return creditRepositoryPort.save(credit);
  }
}
