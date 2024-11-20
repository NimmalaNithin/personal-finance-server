package com.personalfinance.personal_finance_app.dto;

import com.personalfinance.personal_finance_app.model.enums.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MonthlyFinanceDetails {
    private Integer month;
    private BigDecimal totalAmount;
}
