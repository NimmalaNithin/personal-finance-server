package com.personalfinance.personal_finance_app.dto;

import com.personalfinance.personal_finance_app.model.entity.Account;
import com.personalfinance.personal_finance_app.model.enums.AccountType;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
public class AccountCreationResponse {

    private UUID id;

    private String name;

    private AccountType type;

    private BigDecimal balance;

    public AccountCreationResponse(Account account) {
        this.id = account.getId();
        this.name = account.getName();
        this.type = account.getType();
        this.balance = account.getBalance();
    }
}
