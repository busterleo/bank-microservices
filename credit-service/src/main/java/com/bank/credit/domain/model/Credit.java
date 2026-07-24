package com.bank.credit.domain.model;

import com.bank.credit.domain.enums.CreditStatus;
import com.bank.credit.domain.enums.CreditType;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
@Setter
public class Credit {
  private String id;
  private String customerId;
  private CreditType type;
  private BigDecimal amount;
  private BigDecimal balance;
  private CreditStatus status;
  private Integer termMonths;
  private LocalDateTime createdAt;
}
