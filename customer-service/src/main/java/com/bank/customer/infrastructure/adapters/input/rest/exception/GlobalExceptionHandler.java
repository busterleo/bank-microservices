package com.bank.customer.infrastructure.adapters.input.rest.exception;

import com.bank.customer.domain.exception.CustomerAlreadyExistsException;
import com.bank.customer.domain.exception.CustomerNotFoundException;
import com.bank.customer.infrastructure.web.dto.ErrorResponse;
import java.time.OffsetDateTime;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@RestControllerAdvice
public class GlobalExceptionHandler {

  private static final Logger LOG = LoggerFactory.getLogger(GlobalExceptionHandler.class);

  @ExceptionHandler(CustomerAlreadyExistsException.class)
  public Mono<ResponseEntity<ErrorResponse>> handleCustomerAlreadyExists(
      CustomerAlreadyExistsException ex, ServerWebExchange exchange) {

    LOG.error("Customer already exists: {}", ex.getMessage());

    return Mono.just(
        ResponseEntity.status(HttpStatus.CONFLICT)
            .body(buildError("CUSTOMER_ALREADY_EXISTS", ex.getMessage(), exchange)));
  }

  @ExceptionHandler(CustomerNotFoundException.class)
  public Mono<ResponseEntity<ErrorResponse>> handleCustomerNotFound(
      CustomerNotFoundException ex, ServerWebExchange exchange) {

    LOG.error("Customer not found: {}", ex.getMessage());

    return Mono.just(
        ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(buildError("CUSTOMER_NOT_FOUND", ex.getMessage(), exchange)));
  }

  @ExceptionHandler(Exception.class)
  public Mono<ResponseEntity<ErrorResponse>> handleGenericException(
      Exception ex, ServerWebExchange exchange) {

    LOG.error("Unexpected error", ex);

    return Mono.just(
        ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(buildError("INTERNAL_SERVER_ERROR", "An unexpected error occurred", exchange)));
  }

  private ErrorResponse buildError(String code, String message, ServerWebExchange exchange) {

    ErrorResponse response = new ErrorResponse();
    response.setCode(code);
    response.setMessage(message);
    response.setPath(exchange.getRequest().getURI().getPath());
    response.setTraceId(exchange.getRequest().getId());
    response.setTimestamp(Date.from(OffsetDateTime.now().toInstant()));

    return response;
  }
}
