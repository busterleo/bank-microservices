package com.bank.account.domain.exception;

import lombok.Getter;

@Getter
public class CustomerNotFoundException extends RuntimeException {
  private final String customerId;

  public CustomerNotFoundException(String customerId) {
    super("Customer with id " + customerId + " not found");
    this.customerId = customerId;
  }
}
