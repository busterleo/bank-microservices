package com.bank.account.infrastructure.adapters.output.webclient.exception;

import lombok.Getter;

@Getter
public class CustomerClientException extends RuntimeException {
  private final String service;
  private final int statusCode;

  public CustomerClientException(String message, String service, int statusCode) {

    super(message);
    this.service = service;
    this.statusCode = statusCode;
  }
}
