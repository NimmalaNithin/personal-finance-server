package com.personalfinance.personal_finance_app.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class AccountBalanceResponse {
    private UUID accountId;
    private BigDecimal balance;
}
