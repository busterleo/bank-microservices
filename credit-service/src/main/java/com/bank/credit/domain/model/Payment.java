package com.bank.credit.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Payment {

  private String id;
  private String creditId;
  private BigDecimal amount;
  private LocalDateTime createdAt;
}
