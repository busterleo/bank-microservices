package com.bank.account.domain.validation;

import com.bank.account.domain.exception.BusinessException;
import com.bank.account.domain.model.Account;
import com.bank.account.domain.model.CustomerInfo;
import com.bank.account.domain.model.CustomerType;
import com.bank.account.domain.ports.output.AccountRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class AccountValidator {
  private final AccountRepositoryPort accountRepositoryPort;

  public Mono<Account> validate(Account account, CustomerInfo customer) {

    return validateBasicRules(account, customer)
        .then(validateExistingAccounts(account, customer))
        .thenReturn(account);
  }

  private Mono<Void> validateBasicRules(Account account, CustomerInfo customer) {

    return AccountValidationRules.RULES.stream()
        .filter(
            rule ->
                rule.customerCondition().test(customer) && rule.accountCondition().test(account))
        .findFirst()
        .map(rule -> Mono.<Void>error(new BusinessException(rule.message())))
        .orElseGet(Mono::empty);
  }

  private Mono<Void> validateExistingAccounts(Account account, CustomerInfo customer) {

    return accountRepositoryPort
        .findByCustomerId(customer.id(), null)
        .filter(existing -> existing.getAccountType() == account.getAccountType())
        .hasElements()
        .flatMap(
            exists -> {
              if (exists && customer.customerType() == CustomerType.PERSONAL) {
                return Mono.error(
                    new BusinessException("Personal customer already has this account type"));
              }
              return Mono.empty();
            });
  }
}
