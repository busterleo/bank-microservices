package com.bank.credit.domain.validation;

import com.bank.credit.domain.enums.CreditType;
import com.bank.credit.domain.enums.CustomerStatus;
import com.bank.credit.domain.enums.CustomerType;
import java.util.List;

public final class CreditValidationRules {
  private CreditValidationRules() {}

  public static final List<ValidationRule> RULES =
      List.of(

          // Cliente debe estar activo
          new ValidationRule(
              customer -> customer.status() != CustomerStatus.ACTIVE,
              credit -> true,
              "Customer is inactive"),

          // Cliente personal solo puede solicitar créditos personales
          new ValidationRule(
              customer -> customer.customerType() == CustomerType.PERSONAL,
              credit -> credit.getType() == CreditType.BUSINESS,
              "Personal customer cannot request business credits"),

          // Cliente empresarial solo puede solicitar créditos empresariales
          new ValidationRule(
              customer -> customer.customerType() == CustomerType.BUSINESS,
              credit -> credit.getType() == CreditType.PERSONAL,
              "Business customer cannot request personal credits"));
}
