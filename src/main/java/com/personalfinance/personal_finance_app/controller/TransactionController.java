package com.personalfinance.personal_finance_app.controller;

import com.personalfinance.personal_finance_app.dto.TransactionRequest;
import com.personalfinance.personal_finance_app.dto.TransactionSummaryResponse;
import com.personalfinance.personal_finance_app.service.TransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    private TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping("/")
    public ResponseEntity<?> getTransactions(@RequestParam(required=false) Integer page,
                                             @RequestParam(required=false, defaultValue = "10" ) Integer size) {

        return ResponseEntity.ok(page==null
                ? transactionService.getTransactions()
                : transactionService.getPaginatedTransactions( page, size)
        );
    }

    @PostMapping("/")
    public ResponseEntity<TransactionSummaryResponse> createTransaction(
            @RequestBody TransactionRequest transactionRequest) {
        TransactionSummaryResponse savedTransaction = transactionService.createTransaction(transactionRequest);
        return ResponseEntity.ok(savedTransaction);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TransactionSummaryResponse> updateTransaction(
            @PathVariable UUID id,
            @RequestBody TransactionRequest transactionRequest) {
        TransactionSummaryResponse updatedTransaction = transactionService.updateTransaction(id, transactionRequest);
        return ResponseEntity.ok(updatedTransaction);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTransaction(@PathVariable UUID id) {
        transactionService.deleteTransaction(id);
        return ResponseEntity.noContent().build();
    }
}
