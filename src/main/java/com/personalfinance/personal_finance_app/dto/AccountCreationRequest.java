package com.personalfinance.personal_finance_app.dto;

import com.personalfinance.personal_finance_app.model.enums.AccountType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountCreationRequest {

    private String name;

    private AccountType type;
}
