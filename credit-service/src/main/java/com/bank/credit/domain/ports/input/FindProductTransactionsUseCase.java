package com.bank.credit.domain.ports.input;

import com.bank.credit.domain.model.Transaction;
import reactor.core.publisher.Flux;

public interface FindProductTransactionsUseCase {

  /**
   * Obtiene los movimientos de un producto financiero.
   *
   * @param productId identificador del producto (crédito o tarjeta).
   * @return movimientos del producto.
   */
  Flux<Transaction> getTransactions(String productId);
}
