package com.project.Expense.Tracker.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
    private String userName;
    @Column(nullable = false)
    @NotNull
    private String password;
    private String email;
    private List<String> roles;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonBackReference
    private List<Expense> expenses;
}
