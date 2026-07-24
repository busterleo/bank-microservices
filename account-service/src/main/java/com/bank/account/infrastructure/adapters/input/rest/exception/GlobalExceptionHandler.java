package com.bank.account.infrastructure.adapters.input.rest.exception;

import com.bank.account.domain.exception.AccountNotFoundException;
import com.bank.account.domain.exception.BusinessException;
import com.bank.account.domain.exception.CustomerNotFoundException;
import com.bank.account.infrastructure.web.dto.ErrorResponse;
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

  @ExceptionHandler(CustomerNotFoundException.class)
  public Mono<ResponseEntity<ErrorResponse>> handleCustomerNotFound(
      CustomerNotFoundException ex, ServerWebExchange exchange) {

    LOG.error("handleCustomerNotFound: {}", ex.getMessage());
    return Mono.just(
        ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(buildError("CUSTOMER_NOT_FOUND", ex.getMessage(), exchange)));
  }

  @ExceptionHandler(AccountNotFoundException.class)
  public Mono<ResponseEntity<ErrorResponse>> handleAccountNotFound(
      AccountNotFoundException ex, ServerWebExchange exchange) {

    LOG.error("handleAccountNotFound: {}", ex.getMessage());
    return Mono.just(
        ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(buildError("ACCOUNT_NOT_FOUND", ex.getMessage(), exchange)));
  }

  @ExceptionHandler(BusinessException.class)
  public Mono<ResponseEntity<ErrorResponse>> handleBusinessException(
      BusinessException ex, ServerWebExchange exchange) {

    LOG.error("handleBusinessException: {}", ex.getMessage());
    return Mono.just(
        ResponseEntity.status(HttpStatus.CONFLICT)
            .body(buildError("BUSINESS_RULE_VIOLATION", ex.getMessage(), exchange)));
  }

  @ExceptionHandler(Exception.class)
  public Mono<ResponseEntity<ErrorResponse>> handleGenericException(
      Exception ex, ServerWebExchange exchange) {

    LOG.error("handleGenericException", ex);

    return Mono.just(
        ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(buildError("INTERNAL_SERVER_ERROR", "Unexpected error", exchange)));
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
