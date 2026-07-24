package com.bank.credit.infrastructure.adapters.output.persistence.mapper;

import com.bank.credit.domain.model.Credit;
import com.bank.credit.domain.model.Transaction;
import com.bank.credit.infrastructure.entity.CreditDocument;
import com.bank.credit.infrastructure.entity.TransactionDocument;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CreditPersistenceMapper {

  CreditDocument toDocument(Credit credit);

  Credit toDomain(CreditDocument document);

  Transaction toDomain(TransactionDocument document);
}
