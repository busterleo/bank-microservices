package com.bank.account.domain.model;

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
