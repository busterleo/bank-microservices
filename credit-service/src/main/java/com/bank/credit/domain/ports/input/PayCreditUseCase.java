package com.bank.credit.domain.ports.input;

import com.bank.credit.domain.model.Payment;
import java.math.BigDecimal;
import reactor.core.publisher.Mono;

public interface PayCreditUseCase {
  /**
   * Registra un pago sobre un crédito.
   *
   * @param creditId identificador del crédito.
   * @param amount monto del pago.
   * @return pago registrado.
   */
  Mono<Payment> pay(String creditId, BigDecimal amount);
}
