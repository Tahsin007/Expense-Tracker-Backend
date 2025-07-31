package com.project.Expense.Tracker.Service;

import com.project.Expense.Tracker.Entity.Categories;
import com.project.Expense.Tracker.Entity.DTO.TransactionRequest;
import com.project.Expense.Tracker.Entity.Transaction;
import com.project.Expense.Tracker.Entity.User;
import com.project.Expense.Tracker.Exception.ApiException;
import com.project.Expense.Tracker.Repository.AuthRepository;
import com.project.Expense.Tracker.Repository.CategoryRepository;
import com.project.Expense.Tracker.Repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private AuthRepository authRepository;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private CategoryRepository categoryRepository;


    public List<Transaction> getAllTransactions(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = authRepository.findByUserName(authentication.getName());
        return transactionRepository.findByUser_Id(user.getId());
    }

    public Transaction saveTransaction(TransactionRequest request, String userName){
        User user = authRepository.findByUserName(userName);
        Categories category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        Transaction transaction = new Transaction();
        transaction.setUser(user);
        transaction.setCategory(category);
        transaction.setAmount(request.getAmount());
        transaction.setType(request.getType());
        transaction.setDescription(request.getDescription());
        transaction.setDate(request.getDate());

        return transactionRepository.save(transaction);
    }

    public Transaction updateTransactionById(Long id, TransactionRequest updatedTransaction){
        if (updatedTransaction == null) {
            throw new ApiException("Invalid transaction data", HttpStatus.BAD_REQUEST);
        }
        Categories category = categoryRepository.findById(updatedTransaction.getCategoryId()).orElseThrow();

        Transaction existingTransaction = transactionRepository.findById(id)
                .orElseThrow(() -> new ApiException("Transaction not found with id: " + id, HttpStatus.NOT_FOUND));

        existingTransaction.setDescription(updatedTransaction.getDescription());
        existingTransaction.setAmount(updatedTransaction.getAmount());
        existingTransaction.setType(updatedTransaction.getType());
        existingTransaction.setCategory(category); // if applicable
        existingTransaction.setDate(updatedTransaction.getDate()); // if applicable
        return transactionRepository.save(existingTransaction);
    }

    public Optional<Transaction> getTransactionById(Long id){
        return transactionRepository.findById(id);
    }

    public String deleteTransaction(Long id, String userName){
        User user = authRepository.findByUserName(userName);
        Optional<Transaction> expenseById = getTransactionById(id);
        if(expenseById.isPresent() && expenseById.get().getUser().getId().equals(user.getId())){
            transactionRepository.deleteById(id);
            return "Item is deleted";
        }else{
            throw new ApiException("You are not authorized to delete this transaction", HttpStatus.UNAUTHORIZED);
        }


    }

    public List<Transaction> getMonthlyTransactions(String userName, int month, int year) {
        return transactionRepository.findMonthlyTransactions(userName, month, year);
    }

    public List<Transaction> getYearlyTransactions(String userName, int year) {
        return transactionRepository.findYearlyTransactions(userName, year);
    }


    public List<Object[]> getMonthlyReport(Long id){
        return transactionRepository.findMonthlyTotalsByUserId(id);
    }

    public List<Transaction> getCategoryWiseReport(String category){
        return transactionRepository.findByCategory_Name(category);
    }
}
