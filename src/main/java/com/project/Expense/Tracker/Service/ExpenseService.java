package com.project.Expense.Tracker.Service;

import com.project.Expense.Tracker.Entity.Transaction;
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

    public List<Transaction> getAllExpenses(String userName){
        return expenseRepository.findByUser_UserName(userName);
    }

    public Transaction saveExpense(Transaction transaction, String userName){
        User user = authRepository.findByUserName(userName);
        transaction.setUser(user);
        return expenseRepository.save(transaction);
    }

    public Transaction updateExpenseById(Long id, Transaction oldTransaction){
        if(oldTransaction !=null && !oldTransaction.getTitle().isEmpty() && !oldTransaction.getAmount().isNaN()){
            Optional<Transaction> newExpense = getExpenseById(id);
            if(newExpense.isPresent()){
                newExpense.get().setTitle(oldTransaction.getTitle());
                newExpense.get().setDescription(oldTransaction.getDescription());
                newExpense.get().setAmount(oldTransaction.getAmount());
                newExpense.get().setCategory(oldTransaction.getCategory());
                return expenseRepository.save(newExpense.get());
            }
        }
        return null;
    }

    public Optional<Transaction> getExpenseById(Long id){
        return expenseRepository.findById(id);
    }

    public String deleteAnExpense(Long id, String userName){
        User user = authRepository.findByUserName(userName);
        Optional<Transaction> expenseById = getExpenseById(id);
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

    public List<Transaction> getCategoryWiseReport(String category){
        return expenseRepository.findByCategory(category);
    }
}
