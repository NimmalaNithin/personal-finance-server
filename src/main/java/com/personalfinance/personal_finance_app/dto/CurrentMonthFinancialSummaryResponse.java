package com.personalfinance.personal_finance_app.dto;

import com.personalfinance.personal_finance_app.model.enums.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class CurrentMonthFinancialSummaryResponse {
    private UUID accountId;
    private BigDecimal totalAmount;
    private TransactionType type;
    private String monthYear;
}
