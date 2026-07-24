package com.bank.customer.infrastructure.adapters.input.rest.mapper;

import com.bank.customer.domain.model.Customer;
import com.bank.customer.domain.model.CustomerPage;
import com.bank.customer.infrastructure.web.dto.CustomerPageResponse;
import com.bank.customer.infrastructure.web.dto.CustomerRequest;
import com.bank.customer.infrastructure.web.dto.CustomerResponse;
import com.bank.customer.infrastructure.web.dto.CustomerUpdateRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerRestMapper {

  CustomerPageResponse toPageResponse(CustomerPage customerPage);

  Customer toDomainUpdate(CustomerUpdateRequest request);

  Customer toDomain(CustomerRequest request);

  default com.bank.customer.domain.enums.CustomerType toDomain(
      com.bank.customer.infrastructure.web.dto.CustomerType type) {
    if (type == null) {
      return null;
    }
    return com.bank.customer.domain.enums.CustomerType.valueOf(type.name());
  }

  default com.bank.customer.domain.enums.CustomerStatus toDomain(
      com.bank.customer.infrastructure.web.dto.CustomerStatus status) {
    if (status == null) {
      return null;
    }
    return com.bank.customer.domain.enums.CustomerStatus.valueOf(status.name());
  }

  default com.bank.customer.domain.enums.DocumentType toDomain(
      com.bank.customer.infrastructure.web.dto.DocumentType documentType) {

    if (documentType == null) {
      return null;
    }

    return com.bank.customer.domain.enums.DocumentType.valueOf(documentType.name());
  }

  CustomerResponse toResponse(Customer customer);

  default com.bank.customer.infrastructure.web.dto.DocumentType toResponse(
      com.bank.customer.domain.enums.DocumentType documentType) {

    if (documentType == null) {
      return null;
    }

    return com.bank.customer.infrastructure.web.dto.DocumentType.valueOf(documentType.name());
  }
}
