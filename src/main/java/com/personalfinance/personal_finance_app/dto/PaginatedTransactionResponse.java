package com.personalfinance.personal_finance_app.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class PaginatedTransactionResponse {
    private List<TransactionSummaryResponse> transactions;
    private int currentPage;
    private int totalPages;
    private long totalElements;
}
