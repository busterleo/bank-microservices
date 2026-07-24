package com.bank.credit.domain.exception;

public class CustomerAlreadyExistsException extends RuntimeException {
  public CustomerAlreadyExistsException(String documentNumber) {
    super("Customer already exists with document number: " + documentNumber);
  }
}
