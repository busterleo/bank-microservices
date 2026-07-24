package com.bank.account.infrastructure.adapters.input.rest.mapper;

import com.bank.account.domain.model.Account;
import com.bank.account.domain.model.AccountBalance;
import com.bank.account.infrastructure.web.dto.AccountRequest;
import com.bank.account.infrastructure.web.dto.AccountResponse;
import com.bank.account.infrastructure.web.dto.AccountStatus;
import com.bank.account.infrastructure.web.dto.BalanceResponse;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AccountRestMapper {

  AccountResponse toResponse(Account account);

  BalanceResponse toBalanceResponse(AccountBalance balance);

  Account toDomain(AccountRequest request);

  default com.bank.account.domain.enums.AccountStatus toDomain(AccountStatus status) {
    if (status == null) {
      return null;
    }
    return com.bank.account.domain.enums.AccountStatus.valueOf(status.name());
  }
}
