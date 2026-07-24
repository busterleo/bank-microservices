package com.bank.customer.domain.model;

import com.bank.customer.domain.enums.CustomerStatus;
import com.bank.customer.domain.enums.DocumentType;
import com.bank.customer.infrastructure.web.dto.CustomerType;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Customer {

  private String id;
  private CustomerType customerType;
  private DocumentType documentType;
  private String documentNumber;
  private String firstName;
  private String lastName;
  private String businessName;
  private String email;
  private String phone;
  private String address;
  private CustomerStatus status;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;

  public void initializeCreation() {
    LocalDateTime now = LocalDateTime.now();

    this.status = CustomerStatus.ACTIVE;
    this.createdAt = now;
    this.updatedAt = now;
  }

  public void disable() {
    this.status = CustomerStatus.INACTIVE;
    this.updatedAt = LocalDateTime.now();
  }

  public void updateData(Customer customer) {
    if (customer.getFirstName() != null) {
      this.firstName = customer.getFirstName();
    }
    if (customer.getLastName() != null) {
      this.lastName = customer.getLastName();
    }
    if (customer.getEmail() != null) {
      this.email = customer.getEmail();
    }
    if (customer.getPhone() != null) {
      this.phone = customer.getPhone();
    }
    if (customer.getAddress() != null) {
      this.address = customer.getAddress();
    }
    if (customer.getBusinessName() != null) {
      this.address = customer.getBusinessName();
    }
    this.updatedAt = LocalDateTime.now();
  }
}
