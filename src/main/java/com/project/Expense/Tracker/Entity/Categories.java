package com.project.Expense.Tracker.Entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;

@Entity
@Table(name = "categories")
@Component
@Data
public class Categories {
    @Id
    @GeneratedValue
    private long id;
    private String name;
    private String icon;
    private String type;
    @Column(name = "is_default")
    private boolean isDefault;

    // Relationships
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "category")
    private List<Transaction> transactions;

    @OneToMany(mappedBy = "category")
    private List<RecurringTransactions> recurringTransactions;

}
