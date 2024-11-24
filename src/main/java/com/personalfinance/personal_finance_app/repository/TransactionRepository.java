package com.personalfinance.personal_finance_app.repository;

import com.personalfinance.personal_finance_app.dto.MonthlyFinanceDetails;
import com.personalfinance.personal_finance_app.model.entity.Transaction;
import com.personalfinance.personal_finance_app.model.enums.TransactionType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface TransactionRepository extends JpaRepository<Transaction, UUID> {
    List<Transaction> findByAccount_User_IdOrderByTransactionDate(UUID accountId);

    Page<Transaction> findByAccount_User_Id(UUID userId, Pageable pageable);

    @Query("SELECT SUM(t.amount) " +
            "FROM Transaction t " +
            "WHERE t.account.user.id = :userId " +
            "AND t.type = :type " +
            "AND t.transactionDate BETWEEN :startDate AND :endDate"
    )
    BigDecimal findTotalAmountByUserIdAndTypeAndTransactionDateBetween(
            @Param("userId") UUID userId,
            @Param("type")TransactionType type,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate);


@Query("SELECT " +
        "new com.personalfinance.personal_finance_app.dto.MonthlyFinanceDetails(MONTH(t.transactionDate), SUM(t.amount)) " +
        "FROM Transaction t " +
        "WHERE t.account.user.id = :userId " +
        "AND t.type = :type " +
        "AND YEAR(t.transactionDate) = YEAR(CURRENT_DATE())" +
        "GROUP BY YEAR(t.transactionDate), MONTH(t.transactionDate) " +
        "ORDER BY YEAR(t.transactionDate), MONTH(t.transactionDate) DESC"
)
    List<MonthlyFinanceDetails> findMonthlyAmountByUserIdAndType(
            @Param("userId") UUID userId,
            @Param("type") TransactionType type
    );
}
