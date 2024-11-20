package com.personalfinance.personal_finance_app.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class AccountTransactionsResponse {
    private List<TransactionSummaryResponse> transactions;
}
