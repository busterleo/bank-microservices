package com.bank.credit.infrastructure.entity;

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
public class PaymentDocument {

  private String id;
  private BigDecimal amount;
  private LocalDateTime createdAt;
}
