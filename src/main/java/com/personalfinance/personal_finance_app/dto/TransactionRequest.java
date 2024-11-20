package com.personalfinance.personal_finance_app.dto;

import com.personalfinance.personal_finance_app.model.enums.TransactionType;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
public class TransactionRequest {

    private UUID accountId;

    private TransactionType type;

    private BigDecimal amount;

    private String description;

    private LocalDate transactionDate;

//    private UUID categoryId;
}
