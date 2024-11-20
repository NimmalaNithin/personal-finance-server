package com.personalfinance.personal_finance_app.service;

import com.personalfinance.personal_finance_app.model.entity.User;

public interface AuthService {
    String registerUser(User user);

    String verifyUser(User user);
}
