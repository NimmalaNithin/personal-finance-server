package com.personalfinance.personal_finance_app.dto;

import com.personalfinance.personal_finance_app.model.enums.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class MonthlyFinantialSummaryResponse {
    private UUID userId;
    private TransactionType type;
    private List<MonthlyFinanceDetails> monthlyAmountDetails;
}
