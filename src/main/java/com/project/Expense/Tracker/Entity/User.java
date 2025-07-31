package com.project.Expense.Tracker.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
@Entity
@Table(name = "`user`")
@Data
public class User {
    @Id
    @GeneratedValue
    private Long id;
    @Column(nullable = false,unique = true)
    @NotNull
    private String userName;
    @Column(nullable = false)
    @NotNull
    private String password;
    @Column(unique = true)
    private String email;
    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();
    private List<String> roles;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonBackReference
    private List<Transaction> transactionsList;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Categories> categoriesList;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Budgets> budgetsList;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<RecurringTransactions> recurringTransactionsList;
}
