package com.bank.account.domain.ports.output;

import com.bank.account.domain.model.CustomerInfo;
import reactor.core.publisher.Mono;

public interface CustomerClientPort {
  Mono<CustomerInfo> findCustomerById(String customerId);
}
