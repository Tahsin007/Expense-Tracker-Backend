package com.project.Expense.Tracker.Entity.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecurringTransactionSummaryDTO {
    private BigDecimal totalIncome;
    private BigDecimal totalExpense;
}

