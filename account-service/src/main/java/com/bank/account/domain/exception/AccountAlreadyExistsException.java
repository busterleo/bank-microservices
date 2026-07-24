package com.bank.account.domain.exception;

public class AccountAlreadyExistsException extends RuntimeException {

  public AccountAlreadyExistsException(String customerId) {
    super("Customer not found with id: " + customerId);
  }
}
