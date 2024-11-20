package com.personalfinance.personal_finance_app.dto;

import com.personalfinance.personal_finance_app.model.entity.Transaction;
import com.personalfinance.personal_finance_app.model.enums.TransactionType;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
public class TransactionSummaryResponse {

    private UUID id;

    private TransactionType type;

    private BigDecimal amount;

    private String description;

    private LocalDate transactionDate;

    public TransactionSummaryResponse(Transaction transaction) {
        this.id = transaction.getId();
        this.type = transaction.getType();
        this.amount = transaction.getAmount();
        this.description = transaction.getDescription();
        this.transactionDate = transaction.getTransactionDate();
    }
}
