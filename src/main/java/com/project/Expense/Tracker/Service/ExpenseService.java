package com.project.Expense.Tracker.Service;

import com.project.Expense.Tracker.Entity.Expense;
import com.project.Expense.Tracker.Entity.User;
import com.project.Expense.Tracker.Repository.AuthRepository;
import com.project.Expense.Tracker.Repository.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExpenseService {

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private AuthRepository authRepository;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    public List<Expense> getAllExpenses(String userName){
        return expenseRepository.findByUser_UserName(userName);
    }

    public Expense saveExpense(Expense expense, String userName){
        User user = authRepository.findByUserName(userName);
        expense.setUser(user);
        return expenseRepository.save(expense);
    }

    public Expense updateExpenseById(Long id, Expense oldExpense){
        if(oldExpense !=null && !oldExpense.getTitle().isEmpty() && !oldExpense.getAmount().isNaN()){
            Optional<Expense> newExpense = getExpenseById(id);
            if(newExpense.isPresent()){
                newExpense.get().setTitle(oldExpense.getTitle());
                newExpense.get().setDescription(oldExpense.getDescription());
                newExpense.get().setAmount(oldExpense.getAmount());
                newExpense.get().setCategory(oldExpense.getCategory());
                return expenseRepository.save(newExpense.get());
            }
        }
        return null;
    }

    public Optional<Expense> getExpenseById(Long id){
        return expenseRepository.findById(id);
    }

    public String deleteAnExpense(Long id, String userName){
        User user = authRepository.findByUserName(userName);
        Optional<Expense> expenseById = getExpenseById(id);
        if(expenseById.isPresent() && expenseById.get().getId().equals(user.getId())){
            expenseRepository.deleteById(id);
            return "Item is deleted";
        }else{
            return "You are not allowed to delete this expense";
        }


    }

    public List<Object[]> getMonthlyReport(Long id){
        return expenseRepository.findMonthlyTotalsByUserId(id);
    }

    public List<Expense> getCategoryWiseReport(String category){
        return expenseRepository.findByCategory(category);
    }
}
