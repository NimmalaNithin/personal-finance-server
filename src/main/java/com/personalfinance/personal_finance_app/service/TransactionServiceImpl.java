package com.personalfinance.personal_finance_app.service;

import com.personalfinance.personal_finance_app.dto.AccountTransactionsResponse;
import com.personalfinance.personal_finance_app.dto.PaginatedTransactionResponse;
import com.personalfinance.personal_finance_app.dto.TransactionRequest;
import com.personalfinance.personal_finance_app.dto.TransactionSummaryResponse;
import com.personalfinance.personal_finance_app.model.entity.Account;
import com.personalfinance.personal_finance_app.model.entity.Transaction;
import com.personalfinance.personal_finance_app.model.enums.TransactionType;
import com.personalfinance.personal_finance_app.repository.AccountRepository;
import com.personalfinance.personal_finance_app.repository.TransactionRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class TransactionServiceImpl implements TransactionService{
    private TransactionRepository transactionRepository;
    private AccountRepository accountRepository;

    private Account getAccount(UUID accountId) {
        Optional<Account> accountOptional = accountRepository.findById(accountId);
        if (accountOptional.isEmpty()) {
            throw new RuntimeException("Account not found");
        }
        return accountOptional.get();
    }
    @Override
    public TransactionSummaryResponse createTransaction(TransactionRequest transactionRequest) {
        Transaction transaction = new Transaction();
        Account account = getAccount(transactionRequest.getAccountId());

        transaction.setAccount(account);
        transaction.setType(transactionRequest.getType());
        transaction.setAmount(transactionRequest.getAmount());
        transaction.setDescription(transactionRequest.getDescription());

        transaction.setTransactionDate(transactionRequest.getTransactionDate() == null
                ? LocalDate.now()
                : transactionRequest.getTransactionDate()
        );

        if(transaction.getType() == TransactionType.INCOME) {
            account.setBalance(account.getBalance().add(transaction.getAmount()));
        }
        else if(transaction.getType() == TransactionType.EXPENSE) {
            account.setBalance(account.getBalance().subtract(transaction.getAmount()));
        }

        Transaction savedTransaction = transactionRepository.save(transaction);
        return new TransactionSummaryResponse(savedTransaction);
    }

    @Override
    public AccountTransactionsResponse getTransactions(UUID accountId) {
        List<Transaction> transactions = transactionRepository.findByAccountIdOrderByTransactionDate(accountId);
        List<TransactionSummaryResponse> transactionSummaryResponses = transactions.stream()
                .map(TransactionSummaryResponse::new)
                .toList();
        return new AccountTransactionsResponse(transactionSummaryResponses);
    }

    @Override
    public PaginatedTransactionResponse getPaginatedTransactions(UUID accountId, Integer page, Integer size) {
        Page<Transaction> transactionPage = transactionRepository.findByAccountId(accountId,
                PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "transactionDate")));

        List<TransactionSummaryResponse> transactionSummaries = transactionPage.getContent().stream()
                .map(TransactionSummaryResponse::new)
                .toList();

        return new PaginatedTransactionResponse(
                transactionSummaries,
                transactionPage.getNumber(),
                transactionPage.getTotalPages(),
                transactionPage.getTotalElements()
        );
    }

    @Override
    public TransactionSummaryResponse updateTransaction(
            UUID transactionId,
            TransactionRequest transactionRequest) {

        Optional<Transaction> optionalTransaction = transactionRepository.findById(transactionId);
        if (optionalTransaction.isEmpty()) {
            throw new RuntimeException("Transaction not found");
        }
        Transaction transaction = optionalTransaction.get();

        Account account = getAccount(transactionRequest.getAccountId());

        transaction.setAccount(account);
        transaction.setType(transactionRequest.getType());
        transaction.setAmount(transactionRequest.getAmount());
        transaction.setDescription(transactionRequest.getDescription());

        if(transaction.getType() == TransactionType.INCOME) {
            account.setBalance(account.getBalance()
                    .subtract(transaction.getAmount())
                    .add(transactionRequest.getAmount()));
        }
        else if(transaction.getType() == TransactionType.EXPENSE) {
            account.setBalance(account.getBalance()
                    .add(transaction.getAmount())
                    .subtract(transactionRequest.getAmount()));
        }

        Transaction savedTransaction = transactionRepository.save(transaction);
        return new TransactionSummaryResponse(savedTransaction);
    }

    @Override
    public void deleteTransaction(UUID transactionId) {
        Optional<Transaction> optionalTransaction = transactionRepository.findById(transactionId);
        if (optionalTransaction.isEmpty()) {
            throw new RuntimeException("Transaction not found");
        }
        transactionRepository.deleteById(transactionId);
    }
}
