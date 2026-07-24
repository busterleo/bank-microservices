package com.bank.account.domain.model;

import com.bank.account.domain.enums.AccountStatus;
import com.bank.account.domain.enums.AccountType;
import com.bank.account.domain.enums.Currency;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class Account {

  private String id;
  private String customerId;
  private String accountNumber;
  private AccountType accountType;
  private Currency currency;
  private BigDecimal balance;
  private AccountStatus status;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;

  public void activate() {

    this.status = AccountStatus.ACTIVE;
    this.updatedAt = LocalDateTime.now();
  }

  public void disable() {

    this.status = AccountStatus.INACTIVE;
    this.updatedAt = LocalDateTime.now();
  }

  public void initializeAccount() {
    this.balance = BigDecimal.ZERO;
    this.status = AccountStatus.ACTIVE;
    LocalDateTime now = LocalDateTime.now();
    this.createdAt = now;
    this.updatedAt = now;
  }

  public void deposit(BigDecimal amount) {

    if (amount.compareTo(BigDecimal.ZERO) <= 0) {
      throw new IllegalArgumentException("Deposit amount must be positive");
    }
    this.balance = this.balance.add(amount);

    this.updatedAt = LocalDateTime.now();
  }

  public void withdraw(BigDecimal amount) {

    if (amount.compareTo(this.balance) > 0) {
      throw new IllegalStateException("Insufficient balance");
    }
    this.balance = this.balance.subtract(amount);

    this.updatedAt = LocalDateTime.now();
  }
}
