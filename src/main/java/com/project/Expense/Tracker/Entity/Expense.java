package com.project.Expense.Tracker.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.stereotype.Component;
import java.time.LocalDate;

@Component
@Entity
@Table(name = "expense")
@Data
public class Expense {
    @Id
    @GeneratedValue
    private Long id;
    @Column(nullable = false)
    @NotNull
    private String title;
    private String description;
    @Column(nullable = false)
    @NotNull
    private Double amount;
    private String category;
    private LocalDate createdAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDate.now();
    }
}
