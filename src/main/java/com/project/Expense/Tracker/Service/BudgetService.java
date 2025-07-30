package com.project.Expense.Tracker.Service;

import com.project.Expense.Tracker.Entity.Budgets;
import com.project.Expense.Tracker.Entity.User;
import com.project.Expense.Tracker.Repository.AuthRepository;
import com.project.Expense.Tracker.Repository.BudgetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BudgetService {
    @Autowired
    private BudgetRepository budgetRepository;

    @Autowired
    private AuthRepository authRepository;

    public Budgets setMonthlyBudget(String userName, Budgets budget){
        User user = authRepository.findByUserName(userName);
        budget.setUser(user);
        return budgetRepository.save(budget);
    }
}
