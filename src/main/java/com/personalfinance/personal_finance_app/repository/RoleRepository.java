package com.personalfinance.personal_finance_app.repository;

import com.personalfinance.personal_finance_app.model.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RoleRepository extends JpaRepository<Role, UUID> {
    Role findByName(String name);
}
