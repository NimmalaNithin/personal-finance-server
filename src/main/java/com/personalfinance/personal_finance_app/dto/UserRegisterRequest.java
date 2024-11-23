package com.personalfinance.personal_finance_app.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRegisterRequest {
    private String userName;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
}
