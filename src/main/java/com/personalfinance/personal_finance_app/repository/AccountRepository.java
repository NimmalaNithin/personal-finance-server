package com.personalfinance.personal_finance_app.repository;

import com.personalfinance.personal_finance_app.model.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AccountRepository extends JpaRepository<Account, UUID> {
}
