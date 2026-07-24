package com.bank.customer.infrastructure.adapters.output.persistence.mapper;

import com.bank.customer.domain.model.Customer;
import com.bank.customer.infrastructure.entity.CustomerDocument;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerPersistenceMapper {

  CustomerDocument toDocument(Customer customer);

  Customer toDomain(CustomerDocument document);
}
