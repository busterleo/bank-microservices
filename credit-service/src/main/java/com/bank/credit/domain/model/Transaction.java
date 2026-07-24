package com.bank.credit.domain.model;

import com.bank.credit.domain.enums.TransactionType;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Transaction {
  /** Identificador único de la transacción. */
  private String id;

  /** Producto asociado (crédito o tarjeta de crédito). */
  private String productId;

  /** Tipo de movimiento. */
  private TransactionType type;

  /** Monto del movimiento. */
  private BigDecimal amount;

  /** Saldo del producto después de aplicar el movimiento. */
  private BigDecimal balanceAfter;

  /** Descripción del movimiento. */
  private String description;

  /** Fecha y hora del movimiento. */
  private LocalDateTime createdAt;
}
