package com.project.Expense.Tracker.Service;

import com.project.Expense.Tracker.Entity.User;
import com.project.Expense.Tracker.Repository.AuthRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private AuthRepository authRepository;

    public User signUpService(User user){
        return authRepository.save(user);
    }


}
