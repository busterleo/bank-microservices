package com.bank.account.domain.service;

import java.util.concurrent.atomic.AtomicLong;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AccountNumberGenerator {
  private final AtomicLong sequence = new AtomicLong(1000000000);

  public String generate() {
    return "001" + sequence.incrementAndGet();
  }
}
