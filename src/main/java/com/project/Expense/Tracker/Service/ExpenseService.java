package com.project.Expense.Tracker.Service;

import com.project.Expense.Tracker.Entity.Transaction;
import com.project.Expense.Tracker.Entity.User;
import com.project.Expense.Tracker.Exception.ApiException;
import com.project.Expense.Tracker.Repository.AuthRepository;
import com.project.Expense.Tracker.Repository.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
        if(oldTransaction !=null){
            Optional<Transaction> newExpense = getExpenseById(id);
            if(newExpense.isPresent()){
                newExpense.get().setDescription(oldTransaction.getDescription());
                newExpense.get().setAmount(oldTransaction.getAmount());
                newExpense.get().setType(oldTransaction.getType());
                return expenseRepository.save(newExpense.get());
            } else {
                throw new ApiException("Transaction not found with id: " + id, HttpStatus.NOT_FOUND);
            }
        }
        throw new ApiException("Invalid transaction data", HttpStatus.BAD_REQUEST);
    }

    public Optional<Transaction> getExpenseById(Long id){
        return expenseRepository.findById(id);
    }

    public String deleteAnExpense(Long id, String userName){
        User user = authRepository.findByUserName(userName);
        Optional<Transaction> expenseById = getExpenseById(id);
        if(expenseById.isPresent() && expenseById.get().getUser().getId().equals(user.getId())){
            expenseRepository.deleteById(id);
            return "Item is deleted";
        }else{
            throw new ApiException("You are not authorized to delete this transaction", HttpStatus.UNAUTHORIZED);
        }


    }

    public List<Object[]> getMonthlyReport(Long id){
        return expenseRepository.findMonthlyTotalsByUserId(id);
    }

    public List<Transaction> getCategoryWiseReport(String category){
        return expenseRepository.findByCategory_Name(category);
    }
}
