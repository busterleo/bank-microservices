package com.bank.credit.infrastructure.adapters.output.webclient;

import com.bank.credit.domain.model.CustomerInfo;
import com.bank.credit.domain.ports.output.CustomerClientPort;
import com.bank.credit.infrastructure.adapters.output.webclient.exception.CustomerClientException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class CustomerWebClientAdapter implements CustomerClientPort {
  private final WebClient webClient;

  @Override
  public Mono<CustomerInfo> findCustomerById(String customerId) {
    return webClient
        .get()
        .uri("/api/v1/customers/{id}", customerId)
        .retrieve()
        .onStatus(
            HttpStatusCode::is4xxClientError,
            response ->
                Mono.error(
                    new CustomerClientException(
                        "Customer service returned client error",
                        "customer-service",
                        response.statusCode().value())))
        .onStatus(
            HttpStatusCode::is5xxServerError,
            response ->
                Mono.error(
                    new CustomerClientException(
                        "Customer service unavailable",
                        "customer-service",
                        response.statusCode().value())))
        .bodyToMono(CustomerInfo.class);
  }
}
