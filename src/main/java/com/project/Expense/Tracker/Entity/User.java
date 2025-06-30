package com.project.Expense.Tracker.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Entity
@Table(name = "`user`")
@Data
public class User {
    @Id
    @GeneratedValue
    private Long id;
    @Column(nullable = false)
    @NotNull
    private String UserName;
    @Column(nullable = false)
    @NotNull
    private String password;
    private String email;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Expense> expenses;
}
