package com.bank.credit.domain.model;

import java.math.BigDecimal;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Balance {

  /** Identificador del producto (crédito o tarjeta). */
  private String productId;

  /** Saldo pendiente del producto. */
  private BigDecimal balance;

  /**
   * Saldo disponible.
   *
   * <p>Crédito: balance pendiente por pagar.
   *
   * <p>Tarjeta: línea disponible para consumir.
   */
  private BigDecimal availableBalance;
}
