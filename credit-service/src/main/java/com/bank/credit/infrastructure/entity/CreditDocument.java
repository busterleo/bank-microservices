package com.bank.credit.infrastructure.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "credits")
public class CreditDocument {
  @Id private String id;

  /** Id del cliente obtenido desde customer-service. */
  private String customerId;

  /** PERSONAL o BUSINESS. */
  private String type;

  /** Monto aprobado. */
  private BigDecimal amount;

  /** Saldo pendiente. */
  private BigDecimal balance;

  /** Número de cuotas. */
  private Integer termMonths;

  /** Estado del crédito. */
  private String status;

  /** Fecha de creación. */
  private LocalDateTime createdAt;

  /** Última actualización. */
  private LocalDateTime updatedAt;

  /** Historial de pagos. */
  @Builder.Default private List<PaymentDocument> payments = new ArrayList<>();

  /** Movimientos del producto. */
  @Builder.Default private List<TransactionDocument> transactions = new ArrayList<>();
}
