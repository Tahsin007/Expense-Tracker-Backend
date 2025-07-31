package com.project.Expense.Tracker.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;
import org.springframework.stereotype.Component;

import java.util.List;

@Entity
@Table(name = "categories")
@Component
@Data
@ToString(exclude = {"user", "transactions", "recurringTransactions"})
public class Categories {
    @Id
    @GeneratedValue
    private long id;
    private String name;
    private String icon;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CategoryType type;

    @Column(name = "is_default")
    private boolean isDefault;

    // Relationships
    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    @OneToMany(mappedBy = "category")
    private List<Transaction> transactions;

    @OneToMany(mappedBy = "category")
    private List<RecurringTransactions> recurringTransactions;

}

