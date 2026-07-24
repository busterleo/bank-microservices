package com.bank.credit.domain.validation;

import com.bank.credit.domain.model.Credit;
import com.bank.credit.domain.model.CustomerInfo;
import java.util.function.Predicate;

public record ValidationRule(
    Predicate<CustomerInfo> customerCondition, Predicate<Credit> creditCondition, String message) {}
