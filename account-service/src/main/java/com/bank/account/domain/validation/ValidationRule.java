package com.bank.account.domain.validation;

import com.bank.account.domain.model.Account;
import com.bank.account.domain.model.CustomerInfo;
import java.util.function.Predicate;

public record ValidationRule(
    Predicate<CustomerInfo> customerCondition,
    Predicate<Account> accountCondition,
    String message) {}
