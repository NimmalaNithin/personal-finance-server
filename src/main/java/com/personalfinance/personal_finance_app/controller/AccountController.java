package com.personalfinance.personal_finance_app.controller;

import com.personalfinance.personal_finance_app.dto.*;
import com.personalfinance.personal_finance_app.model.enums.TransactionType;
import com.personalfinance.personal_finance_app.service.AccountService;
import com.personalfinance.personal_finance_app.service.TransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    private AccountService accountService;

    public AccountController(AccountService accountService){
        this.accountService = accountService;
    }

    @PostMapping("/")
    public ResponseEntity<AccountCreationResponse> createAccount(
            @RequestBody AccountCreationRequest accountCreationRequest) {
        AccountCreationResponse savedAccount =  accountService.createUser(accountCreationRequest);
        return ResponseEntity.ok(savedAccount);
    }

    @GetMapping("/income/current-month")
    public ResponseEntity<CurrentMonthFinancialSummaryResponse> getCurrentMonthIncome() {
        return ResponseEntity.ok(accountService.getCurrentMonthFinanceSummary(TransactionType.INCOME));
    }

    @GetMapping("/income/monthly")
    public ResponseEntity<MonthlyFinantialSummaryResponse> getMonthlyIncome() {
        return ResponseEntity.ok(accountService.getMonthlyFinanceSummary(TransactionType.INCOME));
    }

    @GetMapping("/expenses/current-month")
    public ResponseEntity<CurrentMonthFinancialSummaryResponse> getCurrentMonthExpenses() {
        return ResponseEntity.ok(accountService.getCurrentMonthFinanceSummary(TransactionType.EXPENSE));
    }

    @GetMapping("/expenses/monthly")
    public ResponseEntity<MonthlyFinantialSummaryResponse> getMonthlyExpenses() {
        return ResponseEntity.ok(accountService.getMonthlyFinanceSummary(TransactionType.EXPENSE));
    }

    @GetMapping("/savings/current-month")
    public ResponseEntity<CurrentMonthFinancialSummaryResponse> getCurrentMonthSavings() {
        return ResponseEntity.ok(accountService.getCurrentMonthSavings());
    }

    @GetMapping("/savings/monthly")
    public ResponseEntity<MonthlyFinantialSummaryResponse> getMonthlySavings() {
        return ResponseEntity.ok(accountService.getMonthlySavings());
    }

    @GetMapping("/{id}/balance")
    public ResponseEntity<AccountBalanceResponse> getBalance(@PathVariable UUID id) {
        return ResponseEntity.ok(accountService.getAccountBalance(id));
    }



}
