package com.personalfinance.personal_finance_app.service;

import com.personalfinance.personal_finance_app.dto.*;
import com.personalfinance.personal_finance_app.model.entity.Account;
import com.personalfinance.personal_finance_app.model.entity.User;
import com.personalfinance.personal_finance_app.model.enums.TransactionType;
import com.personalfinance.personal_finance_app.repository.AccountRepository;
import com.personalfinance.personal_finance_app.repository.TransactionRepository;
import com.personalfinance.personal_finance_app.repository.UserRepository;
import com.personalfinance.personal_finance_app.util.JwtTokenUtil;
import com.personalfinance.personal_finance_app.util.TransactionUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AccountServiceImpl implements AccountService{

    private final TransactionRepository transactionRepository;
    private UserRepository userRepository;
    private AccountRepository accountRepository;

    @Override
    public AccountCreationResponse createUser(AccountCreationRequest accountCreationRequest) {
        UUID userId = JwtTokenUtil.getUserId();

        Account account = new Account();
        account.setName(accountCreationRequest.getName());
        account.setType(accountCreationRequest.getType());
        account.setBalance(BigDecimal.valueOf(0));

        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            throw new RuntimeException("No user");
        }
        account.setUser(user.get());

        Account savedAccount = accountRepository.save(account);
        return new AccountCreationResponse(savedAccount);
    }

    @Override
    public CurrentMonthFinancialSummaryResponse getCurrentMonthFinanceSummary(UUID accountId, TransactionType type) {
        LocalDate startDate = LocalDate.now().withDayOfMonth(1);
        LocalDate endDate = LocalDate.now().withDayOfMonth(startDate.lengthOfMonth());
        BigDecimal income = transactionRepository.findTotalAmountByAccountIdAndTypeAndTransactionDateBetween(
                accountId,
                type,
                startDate,
                endDate
        );

        return new CurrentMonthFinancialSummaryResponse(
                accountId,
                income,
                type,
                startDate.format(DateTimeFormatter.ofPattern("MM-yyyy"))
        );
    }

    @Override
    public CurrentMonthFinancialSummaryResponse getCurrentMonthSavings(UUID accountId) {
        LocalDate startDate = LocalDate.now().withDayOfMonth(1);
        LocalDate endDate = LocalDate.now().withDayOfMonth(startDate.lengthOfMonth());
        BigDecimal income = transactionRepository.findTotalAmountByAccountIdAndTypeAndTransactionDateBetween(
                accountId,
                TransactionType.INCOME,
                startDate,
                endDate
        );

        BigDecimal expense = transactionRepository.findTotalAmountByAccountIdAndTypeAndTransactionDateBetween(
                accountId,
                TransactionType.EXPENSE,
                startDate,
                endDate
        );


        return new CurrentMonthFinancialSummaryResponse(
                accountId,
                income.subtract(expense),
                null,
                startDate.format(DateTimeFormatter.ofPattern("MM-yyyy"))
        );
    }

    @Override
    public AccountBalanceResponse getAccountBalance(UUID accountId) {
        Optional<Account> account = accountRepository.findById(accountId);
        if (account.isEmpty()) {
            throw new RuntimeException("Account not found");
        }

        return new AccountBalanceResponse(accountId, account.get().getBalance());
    }

    @Override
    public MonthlyFinantialSummaryResponse getMonthlyFinanceSummary(UUID accountId, TransactionType transactionType) {
        List<MonthlyFinanceDetails> monthlyFinances = transactionRepository.
                findMonthlyIncomeByAccountIdAndType(accountId, transactionType);
        return new MonthlyFinantialSummaryResponse(accountId, transactionType, monthlyFinances);
    }

    @Override
    public MonthlyFinantialSummaryResponse getMonthlySavings(UUID accountId) {
        List<MonthlyFinanceDetails> monthlyIncomes = transactionRepository.
                findMonthlyIncomeByAccountIdAndType(accountId, TransactionType.INCOME);

        List<MonthlyFinanceDetails> monthlyExpenses = transactionRepository.
                findMonthlyIncomeByAccountIdAndType(accountId, TransactionType.EXPENSE);

        List<MonthlyFinanceDetails> monthlySavings = TransactionUtil.getMonthlyExpenses(
                monthlyIncomes,
                monthlyExpenses
        );
        return new MonthlyFinantialSummaryResponse(accountId, null, monthlySavings);
    }
}
