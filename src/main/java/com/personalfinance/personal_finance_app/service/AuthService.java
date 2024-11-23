package com.personalfinance.personal_finance_app.service;

import com.personalfinance.personal_finance_app.dto.UserLoginRequest;
import com.personalfinance.personal_finance_app.dto.UserRegisterRequest;
import com.personalfinance.personal_finance_app.model.entity.User;

public interface AuthService {
    String registerUser(UserRegisterRequest userRegisterRequest);

    String verifyUser(UserLoginRequest userLoginRequest);
}
