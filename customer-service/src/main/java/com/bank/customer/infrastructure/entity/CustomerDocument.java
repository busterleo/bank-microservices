package com.bank.customer.infrastructure.entity;

import java.time.LocalDateTime;
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
@Document(collection = "customers")
public class CustomerDocument {
  @Id private String id;
  private String customerType;
  private String documentType;
  private String documentNumber;
  private String firstName;
  private String lastName;
  private String businessName;
  private String email;
  private String phone;
  private String address;
  private String status;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
}
