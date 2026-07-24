package com.bank.account.infrastructure.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "accounts")
public class AccountDocument {
  @Id private String id;
  private String customerId;
  private String accountNumber;
  private String accountType;
  private String currency;
  private BigDecimal balance;
  private String status;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
}
