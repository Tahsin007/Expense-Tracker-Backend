package com.project.Expense.Tracker.Entity.DTO;

import com.project.Expense.Tracker.Entity.CategoryType;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class TransactionRequest {
    private BigDecimal amount;
    private CategoryType type;
    private String description;
    private LocalDate date;
    private Long categoryId;
}
