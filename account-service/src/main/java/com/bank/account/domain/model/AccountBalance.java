package com.bank.account.domain.model;

import java.math.BigDecimal;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AccountBalance {

  private String accountId;
  private BigDecimal balance;
  private BigDecimal availableBalance;
}
