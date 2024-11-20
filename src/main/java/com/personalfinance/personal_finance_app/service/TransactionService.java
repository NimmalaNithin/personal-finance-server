package com.personalfinance.personal_finance_app.service;

import com.personalfinance.personal_finance_app.dto.AccountTransactionsResponse;
import com.personalfinance.personal_finance_app.dto.PaginatedTransactionResponse;
import com.personalfinance.personal_finance_app.dto.TransactionRequest;
import com.personalfinance.personal_finance_app.dto.TransactionSummaryResponse;

import java.util.UUID;

public interface TransactionService {
    TransactionSummaryResponse createTransaction(TransactionRequest transactionRequest);

    AccountTransactionsResponse getTransactions(UUID accountId);

    PaginatedTransactionResponse getPaginatedTransactions(UUID accountId, Integer page, Integer size);

    void deleteTransaction(UUID transactionId);

    TransactionSummaryResponse updateTransaction(UUID transactionId, TransactionRequest transactionRequest);
}
