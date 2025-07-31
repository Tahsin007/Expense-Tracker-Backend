package com.project.Expense.Tracker.Service;

import com.project.Expense.Tracker.Entity.Budgets;
import com.project.Expense.Tracker.Entity.User;
import com.project.Expense.Tracker.Exception.ResourceNotFoundException;
import com.project.Expense.Tracker.Repository.AuthRepository;
import com.project.Expense.Tracker.Repository.BudgetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BudgetService {
    @Autowired
    private BudgetRepository budgetRepository;

    @Autowired
    private AuthRepository authRepository;

    public Budgets setMonthlyBudget(String userName, Budgets budget){
        if(budget.getMonth()>12){
            return null;
        }
        User user = authRepository.findByUserName(userName);
        budget.setUser(user);
        return budgetRepository.save(budget);
    }

    public List<Budgets> getAllBudgets(String userName){
        User user = authRepository.findByUserName(userName);
        return budgetRepository.findByUser_UserName(user.getUserName());
    }

    public Optional<Budgets> getBudgetByMonth(String userName, int month){
        User user = authRepository.findByUserName(userName);
        return budgetRepository.findByUser_UserNameAndMonth(user.getUserName(),month);
    }

    public Optional<List<Budgets>> getBudgetsByYear(String userName , int year){
        User user = authRepository.findByUserName(userName);
        return budgetRepository.findByUser_UserNameAndYear(user.getUserName(),year);
    }

    public Budgets updateBudget(Long id, Budgets updatedBudget, String userName) {
        User user = authRepository.findByUserName(userName);
        Budgets budget = budgetRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Budget not found"));

        if (!budget.getUser().getId().equals(user.getId())) {
            throw new AccessDeniedException("You are not allowed to update this budget.");
        }

        budget.setAmount(updatedBudget.getAmount());
        budget.setMonth(updatedBudget.getMonth());

        return budgetRepository.save(budget);
    }


    public void deleteBudget(Long id, Long userId) {
        Budgets budget = budgetRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Budget not found"));

        if (!budget.getUser().getId().equals(userId)) {
            throw new AccessDeniedException("You are not allowed to delete this budget.");
        }

        budgetRepository.delete(budget);
    }


}
