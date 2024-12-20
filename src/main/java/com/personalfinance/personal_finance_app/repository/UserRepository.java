package com.personalfinance.personal_finance_app.repository;

import com.personalfinance.personal_finance_app.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    User findByUserName(String username);
}
