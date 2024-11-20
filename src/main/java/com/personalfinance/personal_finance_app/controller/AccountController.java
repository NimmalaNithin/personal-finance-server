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
    private TransactionService transactionService;

    public AccountController(AccountService accountService, TransactionService transactionService){
        this.accountService = accountService;
        this.transactionService = transactionService;
    }

    @PostMapping("/")
    public ResponseEntity<AccountCreationResponse> createAccount(
            @RequestBody AccountCreationRequest accountCreationRequest) {
        AccountCreationResponse savedAccount =  accountService.createUser(accountCreationRequest);
        return ResponseEntity.ok(savedAccount);
    }

    @GetMapping("/{id}/transactions")
    public ResponseEntity<?> getTransactions(@PathVariable UUID id,
                                             @RequestParam(required=false) Integer page,
                                             @RequestParam(required=false, defaultValue = "10" ) Integer size) {
         return ResponseEntity.ok(page==null
                ? transactionService.getTransactions(id)
                : transactionService.getPaginatedTransactions(id, page, size)
         );
    }

    @GetMapping("/{id}/income/current-month")
    public ResponseEntity<CurrentMonthFinancialSummaryResponse> getCurrentMonthIncome(@PathVariable UUID id) {
        return ResponseEntity.ok(accountService.getCurrentMonthFinanceSummary(id, TransactionType.INCOME));
    }

    @GetMapping("/{id}/income/monthly")
    public ResponseEntity<MonthlyFinantialSummaryResponse> getMonthlyIncome(@PathVariable UUID id) {
        return ResponseEntity.ok(accountService.getMonthlyFinanceSummary(id, TransactionType.INCOME));
    }

    @GetMapping("/{id}/expenses/current-month")
    public ResponseEntity<CurrentMonthFinancialSummaryResponse> getCurrentMonthExpenses(@PathVariable UUID id) {
        return ResponseEntity.ok(accountService.getCurrentMonthFinanceSummary(id, TransactionType.EXPENSE));
    }

    @GetMapping("/{id}/expenses/monthly")
    public ResponseEntity<MonthlyFinantialSummaryResponse> getMonthlyExpenses(@PathVariable UUID id) {
        return ResponseEntity.ok(accountService.getMonthlyFinanceSummary(id, TransactionType.EXPENSE));
    }

    @GetMapping("/{id}/savings/current-month")
    public ResponseEntity<CurrentMonthFinancialSummaryResponse> getCurrentMonthSavings(@PathVariable UUID id) {
        return ResponseEntity.ok(accountService.getCurrentMonthSavings(id));
    }

    @GetMapping("/{id}/savings/monthly")
    public ResponseEntity<MonthlyFinantialSummaryResponse> getMonthlySavings(@PathVariable UUID id) {
        return ResponseEntity.ok(accountService.getMonthlySavings(id));
    }

    @GetMapping("/{id}/balance")
    public ResponseEntity<AccountBalanceResponse> getBalance(@PathVariable UUID id) {
        return ResponseEntity.ok(accountService.getAccountBalance(id));
    }



}
