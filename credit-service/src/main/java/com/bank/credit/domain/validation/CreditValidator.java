package com.bank.credit.domain.validation;

import com.bank.credit.domain.enums.CreditStatus;
import com.bank.credit.domain.enums.CustomerType;
import com.bank.credit.domain.exception.BusinessException;
import com.bank.credit.domain.model.Credit;
import com.bank.credit.domain.model.CustomerInfo;
import com.bank.credit.domain.ports.output.CreditRepositoryPort;
import com.bank.credit.domain.ports.output.CustomerClientPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class CreditValidator {
  private final CustomerClientPort customerClientPort;
  private final CreditRepositoryPort creditRepositoryPort;

  public Mono<Void> validate(Credit credit) {

    return customerClientPort
        .findCustomerById(credit.getCustomerId())
        .flatMap(
            customer ->
                validateRules(customer, credit)
                    .then(validatePersonalCustomerCredit(customer, credit)));
  }

  private Mono<Void> validateRules(CustomerInfo customer, Credit credit) {

    for (ValidationRule rule : CreditValidationRules.RULES) {

      if (rule.customerCondition().test(customer) && rule.creditCondition().test(credit)) {

        return Mono.error(new BusinessException(rule.message()));
      }
    }

    return Mono.empty();
  }

  private Mono<Void> validatePersonalCustomerCredit(CustomerInfo customer, Credit credit) {

    if (customer.customerType() != CustomerType.PERSONAL) {
      return Mono.empty();
    }
    return creditRepositoryPort
        .findByCustomer(customer.id(), null, CreditStatus.ACTIVE)
        .hasElements()
        .flatMap(
            exists ->
                exists
                    ? Mono.error(
                        new BusinessException("Personal customer already has an active credit"))
                    : Mono.empty());
  }
}
