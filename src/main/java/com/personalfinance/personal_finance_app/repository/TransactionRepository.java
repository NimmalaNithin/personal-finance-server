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
    List<Transaction> findByAccountIdOrderByTransactionDate(UUID accountId);

    Page<Transaction> findByAccountId(UUID accountId, Pageable pageable);

    @Query("SELECT SUM(t.amount) " +
            "FROM Transaction t " +
            "WHERE t.account.id = :accountId " +
            "AND t.type = :type " +
            "AND t.transactionDate BETWEEN :startDate AND :endDate"
    )
    BigDecimal findTotalAmountByAccountIdAndTypeAndTransactionDateBetween(
            @Param("accountId") UUID accountId,
            @Param("type")TransactionType type,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate);

//    @Query("SELECT new com.personalfinance.personal_finance_app.dto.MonthlyFinanceDetails(" +
//            "MONTH(t.transactionDate), SUM(t.amount)" +
//            ") " +
//            "FROM Transaction t " +
//            "WHERE t.account.id = :accountId " +
//            "AND t.type = :type " +
//            "GROUP BY FUNCTION('DATE_FORMAT', t.transactionDate, '%m-%Y')" +
//            "ORDER BY FUNCTION('DATE_FORMAT', t.transactionDate, '%m-%Y') DESC")
//@Query("SELECT new com.personalfinance.personal_finance_app.dto.MonthlyFinanceDetails(" +
//        " t.type, SUM(t.amount)" +
//        ") " +
//        "FROM Transaction t " +
//        "WHERE t.account.id = :accountId " +
//        "AND t.type = :type group by t.type"
//)
@Query("SELECT new com.personalfinance.personal_finance_app.dto.MonthlyFinanceDetails(MONTH(t.transactionDate), SUM(t.amount)) " +
        "FROM Transaction t " +
        "WHERE t.account.id = :accountId " +
        "AND t.type = :type " +
        "AND YEAR(t.transactionDate) = YEAR(CURRENT_DATE())" +
        "GROUP BY YEAR(t.transactionDate), MONTH(t.transactionDate) " +
        "ORDER BY YEAR(t.transactionDate), MONTH(t.transactionDate) DESC"
)
    List<MonthlyFinanceDetails> findMonthlyIncomeByAccountIdAndType(
            @Param("accountId") UUID accountId,
            @Param("type") TransactionType type
    );
}
