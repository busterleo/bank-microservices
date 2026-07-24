package com.bank.account.infrastructure.adapters.output.persistence.mapper;

import com.bank.account.domain.enums.AccountStatus;
import com.bank.account.domain.enums.AccountType;
import com.bank.account.domain.enums.Currency;
import com.bank.account.domain.model.Account;
import com.bank.account.infrastructure.entity.AccountDocument;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AccountPersistenceMapper {
  AccountDocument toDocument(Account account);

  Account toDomain(AccountDocument document);

  default String map(AccountType value) {
    return value != null ? value.name() : null;
  }

  default String map(Currency value) {
    return value != null ? value.name() : null;
  }

  default String map(AccountStatus value) {
    return value != null ? value.name() : null;
  }

  default AccountType mapAccountType(String value) {
    return value != null ? AccountType.valueOf(value) : null;
  }

  default AccountStatus mapAccountStatus(String value) {
    return value != null ? AccountStatus.valueOf(value) : null;
  }

  default Currency mapCurrency(String value) {
    return value != null ? Currency.valueOf(value) : null;
  }
}
