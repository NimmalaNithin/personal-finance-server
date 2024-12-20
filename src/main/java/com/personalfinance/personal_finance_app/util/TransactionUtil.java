package com.personalfinance.personal_finance_app.util;

import com.personalfinance.personal_finance_app.dto.MonthlyFinanceDetails;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class TransactionUtil {
    public static List<MonthlyFinanceDetails> getMonthlyExpenses(
            List<MonthlyFinanceDetails> monthlyIncomes, List<MonthlyFinanceDetails> monthlyExpenses) {
        List<MonthlyFinanceDetails> monthlySavings = new ArrayList<>();
        int incomesPointer = 0;
        int expensesPointer = 0;

        while(incomesPointer < monthlyIncomes.size() && expensesPointer < monthlyExpenses.size()) {
            MonthlyFinanceDetails monthlySaving = new MonthlyFinanceDetails();

            if(monthlyIncomes.get(incomesPointer).getMonth()
                    .equals(monthlyExpenses.get(expensesPointer).getMonth())) {

                monthlySaving.setTotalAmount(monthlyIncomes
                        .get(incomesPointer).getTotalAmount()
                        .subtract(monthlyExpenses.get(expensesPointer).getTotalAmount())
                );
                monthlySaving.setMonth(monthlyIncomes.get(incomesPointer).getMonth());

                monthlySavings.add(monthlySaving);

                incomesPointer += 1;
                expensesPointer += 1;
            }

            else if(monthlyIncomes.get(incomesPointer).getMonth()
                    > monthlyExpenses.get(expensesPointer).getMonth()) {

                monthlySaving.setTotalAmount(monthlyIncomes
                        .get(incomesPointer).getTotalAmount()
                );
                monthlySaving.setMonth(monthlyIncomes.get(incomesPointer).getMonth());
                monthlySavings.add(monthlySaving);
                incomesPointer += 1;
            }

            else if(monthlyIncomes.get(incomesPointer).getMonth()
                    < monthlyExpenses.get(expensesPointer).getMonth()) {

                monthlySaving.setTotalAmount(monthlyExpenses
                        .get(expensesPointer).getTotalAmount()
                        .multiply(new BigDecimal(-1))
                );
                monthlySaving.setMonth(monthlyExpenses.get(expensesPointer).getMonth());
                monthlySavings.add(monthlySaving);
                expensesPointer += 1;
            }
        }

        while(incomesPointer < monthlyIncomes.size()) {
            MonthlyFinanceDetails monthlySaving = new MonthlyFinanceDetails();

            monthlySaving.setTotalAmount(monthlyIncomes
                    .get(incomesPointer).getTotalAmount()
            );
            monthlySaving.setMonth(monthlyIncomes.get(incomesPointer).getMonth());
            monthlySavings.add(monthlySaving);
            incomesPointer += 1;
        }

        while(expensesPointer < monthlyExpenses.size()) {
            MonthlyFinanceDetails monthlySaving = new MonthlyFinanceDetails();

            monthlySaving.setTotalAmount(monthlyExpenses
                    .get(expensesPointer).getTotalAmount()
                    .multiply(new BigDecimal(-1))
            );
            monthlySaving.setMonth(monthlyExpenses.get(expensesPointer).getMonth());
            monthlySavings.add(monthlySaving);
            expensesPointer += 1;
        }

        return monthlySavings;
    }
}
