package com.bank.account.domain.validation;

import com.bank.account.domain.enums.AccountType;
import com.bank.account.domain.model.CustomerStatus;
import com.bank.account.domain.model.CustomerType;
import java.util.List;

public final class AccountValidationRules {
  private AccountValidationRules() {
    // Evita instanciación
  }

  public static final List<ValidationRule> RULES =
      List.of(
          // Regla 1: Cliente debe estar activo
          new ValidationRule(
              customer -> customer.status() != CustomerStatus.ACTIVE,
              account -> true,
              "Customer is inactive"),
          // Regla 2: Empresa no puede tener cuenta de ahorro
          new ValidationRule(
              customer -> customer.customerType() == CustomerType.BUSINESS,
              account -> account.getAccountType() == AccountType.SAVINGS,
              "Business customers cannot have savings accounts"),
          // Regla 3: Empresa no puede tener plazo fijo
          new ValidationRule(
              customer -> customer.customerType() == CustomerType.BUSINESS,
              account -> account.getAccountType() == AccountType.FIXED_TERM,
              "Business customers cannot have fixed-term accounts"));
}
