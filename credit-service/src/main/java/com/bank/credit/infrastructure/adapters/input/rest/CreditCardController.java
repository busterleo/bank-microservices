package com.bank.credit.infrastructure.adapters.input.rest;

import com.bank.credit.infrastructure.web.api.CreditCardsApi;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CreditCardController implements CreditCardsApi {}
