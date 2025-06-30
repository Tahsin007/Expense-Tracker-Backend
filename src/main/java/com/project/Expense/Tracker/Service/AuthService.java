package com.project.Expense.Tracker.Service;

import com.project.Expense.Tracker.Entity.User;
import com.project.Expense.Tracker.Repository.AuthRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class AuthService {
    @Autowired
    private AuthRepository authRepository;

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public User signUpService(User user){
        if (!user.getPassword().startsWith("$2a$")) { // BCrypt hashes start with $2a$
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        user.setRoles(List.of("USER"));
        return authRepository.save(user);
    }

    public void signInService(){


    }


}
