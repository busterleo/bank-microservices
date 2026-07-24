package com.bank.account.application.usecase;

import com.bank.account.domain.exception.CustomerNotFoundException;
import com.bank.account.domain.model.Account;
import com.bank.account.domain.ports.input.CreateAccountUseCase;
import com.bank.account.domain.ports.output.AccountRepositoryPort;
import com.bank.account.domain.ports.output.CustomerClientPort;
import com.bank.account.domain.service.AccountNumberGenerator;
import com.bank.account.domain.validation.AccountValidator;
import com.bank.account.infrastructure.adapters.output.webclient.exception.CustomerClientException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class CreateAccountUseCaseImpl implements CreateAccountUseCase {
  private final AccountRepositoryPort accountRepository;
  private final CustomerClientPort customerClientPort;
  private final AccountValidator accountValidator;
  private final AccountNumberGenerator accountNumberGenerator;
  private static final Logger LOG = LoggerFactory.getLogger(CreateAccountUseCaseImpl.class);

  @Override
  public Mono<Account> create(Account account) {

    LOG.info("Creating account for customerId={}", account.getCustomerId());

    return customerClientPort
        .findCustomerById(account.getCustomerId())
        .onErrorResume(
            CustomerClientException.class,
            ex -> Mono.error(new CustomerNotFoundException(account.getCustomerId())))
        .flatMap(customer -> accountValidator.validate(account, customer))
        .map(
            validAccount -> {
              validAccount.initializeAccount();
              return validAccount;
            })
        .flatMap(this::generateAccountNumber)
        .flatMap(accountRepository::save);
  }

  private Mono<Account> generateAccountNumber(Account account) {
    String accountNumber = accountNumberGenerator.generate();
    account.setAccountNumber(accountNumber);
    return Mono.just(account);
  }
}
