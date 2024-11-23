package com.personalfinance.personal_finance_app.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserLoginRequest {
    private String userName;
    private String password;
}
