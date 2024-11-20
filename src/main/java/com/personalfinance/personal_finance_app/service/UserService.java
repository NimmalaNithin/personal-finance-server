package com.personalfinance.personal_finance_app.service;

import com.personalfinance.personal_finance_app.model.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.UUID;

public interface UserService extends UserDetailsService {
    List<User> findAll();

    String delete(UUID id);
}
