package com.bank.credit.domain.model;

import com.bank.credit.domain.enums.CustomerStatus;
import com.bank.credit.domain.enums.CustomerType;
import lombok.Builder;

@Builder
public record CustomerInfo(
    String id,
    CustomerType customerType,
    CustomerStatus status,
    String documentNumber,
    String firstName,
    String lastName,
    String businessName) {}
