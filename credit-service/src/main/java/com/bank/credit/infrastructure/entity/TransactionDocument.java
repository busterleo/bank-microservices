package com.bank.credit.infrastructure.entity;

import com.bank.credit.domain.enums.TransactionType;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDocument {
  private String id;

  private TransactionType type;

  private BigDecimal amount;

  private BigDecimal balanceAfter;

  private LocalDateTime createdAt;

  private String description;
}
