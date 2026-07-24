package com.bank.customer.domain.exception;

public class CustomerNotFoundException extends RuntimeException {

  public CustomerNotFoundException(String customerId) {
    super("Customer not found with id: " + customerId);
  }

  public CustomerNotFoundException(String field, String value) {
    super("Customer not found with " + field + ": " + value);
  }
}
