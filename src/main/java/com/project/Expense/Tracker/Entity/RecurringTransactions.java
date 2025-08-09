package com.project.Expense.Tracker.Entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "recurring_transactions")
@Component
@Data
public class RecurringTransactions {
    @Id
    @GeneratedValue
    private Long id;
    private BigDecimal amount;
    private String type;
    private String description;
    private String frequency;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalDate nextOccurrence;

    // Relationships
    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    @ManyToOne
    @JoinColumn(name = "category_id")
    @JsonIgnore
    private Categories category;
}
