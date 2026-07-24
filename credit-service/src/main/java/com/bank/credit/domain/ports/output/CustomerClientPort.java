package com.bank.credit.domain.ports.output;

import com.bank.credit.domain.model.CustomerInfo;
import reactor.core.publisher.Mono;

public interface CustomerClientPort {
  Mono<CustomerInfo> findCustomerById(String customerId);
}
