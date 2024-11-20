package com.personalfinance.personal_finance_app.service;

import com.personalfinance.personal_finance_app.dto.*;
import com.personalfinance.personal_finance_app.model.entity.Account;
import com.personalfinance.personal_finance_app.model.enums.TransactionType;

import java.util.UUID;

public interface AccountService {
    AccountCreationResponse createUser(AccountCreationRequest accountCreationRequest);

    CurrentMonthFinancialSummaryResponse getCurrentMonthFinanceSummary(UUID accountId, TransactionType type);

    CurrentMonthFinancialSummaryResponse getCurrentMonthSavings(UUID accountId);

    AccountBalanceResponse getAccountBalance(UUID accountId);

    MonthlyFinantialSummaryResponse getMonthlyFinanceSummary(UUID accountId, TransactionType transactionType);

    MonthlyFinantialSummaryResponse getMonthlySavings(UUID accountId);
}
