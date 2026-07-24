package com.bank.credit.infrastructure.adapters.input.rest.mapper;

import com.bank.credit.domain.model.Balance;
import com.bank.credit.domain.model.Credit;
import com.bank.credit.domain.model.Payment;
import com.bank.credit.domain.model.Transaction;
import com.bank.credit.infrastructure.web.dto.BalanceResponse;
import com.bank.credit.infrastructure.web.dto.CreditRequest;
import com.bank.credit.infrastructure.web.dto.CreditResponse;
import com.bank.credit.infrastructure.web.dto.PaymentResponse;
import com.bank.credit.infrastructure.web.dto.TransactionResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CreditRestMapper {

  CreditResponse toResponse(Credit credit);

  PaymentResponse toPaymentResponse(Payment payment);

  @Mapping(target = "productId", source = "productId")
  @Mapping(target = "balance", source = "balance")
  @Mapping(target = "availableBalance", source = "availableBalance")
  BalanceResponse toBalanceResponse(Balance balance);

  TransactionResponse toTransactionResponse(Transaction transaction);

  Credit toDomain(CreditRequest request);

  default com.bank.credit.domain.enums.CreditType toDomain(
      com.bank.credit.infrastructure.web.dto.CreditType type) {

    return type == null ? null : com.bank.credit.domain.enums.CreditType.valueOf(type.name());
  }

  default com.bank.credit.domain.enums.CreditStatus toDomain(
      com.bank.credit.infrastructure.web.dto.CreditStatus status) {

    return status == null ? null : com.bank.credit.domain.enums.CreditStatus.valueOf(status.name());
  }
}
