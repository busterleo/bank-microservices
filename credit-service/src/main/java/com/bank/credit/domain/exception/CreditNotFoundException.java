package com.bank.credit.domain.exception;

public class CreditNotFoundException extends RuntimeException {

  public CreditNotFoundException(String value) {
    super("Credit with id " + value + " not found");
  }
}
