package com.bank.account.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AccountType {
  SAVINGS("Cuenta de ahorro"),
  CHECKING("Cuenta corriente"),
  FIXED_TERM("Cuenta a plazo fijo");
  private final String description;
}
