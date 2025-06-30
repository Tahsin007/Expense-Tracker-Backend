package com.project.Expense.Tracker.Service;

import com.project.Expense.Tracker.Entity.Expense;
import com.project.Expense.Tracker.Repository.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExpenseService {

    @Autowired
    private ExpenseRepository expenseRepository;

    public List<Expense> getAllExpenses(){
        return expenseRepository.findAll();
    }

    public Expense saveExpense(Expense expense){
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

    public void deleteAnExpense(Long id){
        expenseRepository.deleteById(id);
    }

    public List<Expense> getMonthlyReport(){
        return expenseRepository.findCurrentMonthExpenses();
    }

    public List<Expense> getCategoryWiseReport(String category){
        return expenseRepository.findByCategory(category);
    }
}
