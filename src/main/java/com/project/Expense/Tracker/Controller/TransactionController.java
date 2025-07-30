package com.project.Expense.Tracker.Controller;

import com.project.Expense.Tracker.Entity.Transaction;
import com.project.Expense.Tracker.Exception.ApiException;
import com.project.Expense.Tracker.Service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/expenses")
public class TransactionController {
    @Autowired
    private ExpenseService expenseService;


    @GetMapping
    public ResponseEntity<?> getAllExpenses(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<Transaction> allExpens = expenseService.getAllExpenses(authentication.getName());
        return new ResponseEntity<>(allExpens, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> addExpense(@RequestBody Transaction transaction){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Transaction transaction1 = expenseService.saveExpense(transaction,authentication.getName());
        return new ResponseEntity<>(transaction1,HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteExpense(@PathVariable Long id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return new ResponseEntity<>(expenseService.deleteAnExpense(id,authentication.getName()), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Transaction> updateExpenseById(@PathVariable Long id, @RequestBody Transaction transaction){
        return new ResponseEntity<>(expenseService.updateExpenseById(id, transaction), HttpStatus.OK);
    }

    @GetMapping("/monthly-report/{id}")
    public ResponseEntity<?> getMonthlyReport(@RequestParam Long id) {
        return ResponseEntity.ok(expenseService.getMonthlyReport(id));
    }

    @GetMapping("/category-report/{category}")
    public ResponseEntity<List<Transaction>> getCategoryWiseReport(@PathVariable String category) {
        return ResponseEntity.ok(expenseService.getCategoryWiseReport(category));
    }

}
