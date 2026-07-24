package com.bank.credit.domain.ports.input;

import com.bank.credit.domain.model.Balance;
import reactor.core.publisher.Mono;

public interface GetProductBalanceUseCase {
  /**
   * Obtiene el saldo del producto.
   *
   * @param productId identificador del producto.
   * @return saldo del producto.
   */
  Mono<Balance> getBalance(String productId);
}
