package com.personalfinance.personal_finance_app.service;

import com.personalfinance.personal_finance_app.model.entity.Role;

import java.util.List;

public interface RoleService {
    List<Role> findAll();

    Role save(Role role);
}
