package com.bank.account.infrastructure.adapters.output.persistence.mapper;

import com.bank.account.domain.model.AccountBalance;
import com.bank.account.infrastructure.entity.AccountDocument;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AccountBalanceMapper {
  @Mapping(source = "id", target = "accountId")
  @Mapping(source = "balance", target = "balance")
  @Mapping(source = "balance", target = "availableBalance")
  AccountBalance toDomain(AccountDocument document);
}
