package com.project.Expense.Tracker.Controller;

import com.project.Expense.Tracker.Entity.Expense;
import com.project.Expense.Tracker.Service.ExpenseService;
import com.project.Expense.Tracker.Service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/expenses")
public class ExpenseController {
    @Autowired
    private ExpenseService expenseService;


    @GetMapping
    public ResponseEntity<?> getAllExpenses(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<Expense> allExpenses = expenseService.getAllExpenses(authentication.getName());
        return new ResponseEntity<>(allExpenses, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Expense> addExpense(@RequestBody Expense  expense){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Expense expense1 = expenseService.saveExpense(expense,authentication.getName());

        return new ResponseEntity<>(expense1,HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public String deleteExpense(@PathVariable Long id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return expenseService.deleteAnExpense(id,authentication.getName());
    }

    @PutMapping("/{id}")
    public Expense updateExpenseById(@PathVariable Long id, @RequestBody Expense expense){
        return expenseService.updateExpenseById(id,expense);
    }

    @GetMapping("/monthly-report/{id}")
    public ResponseEntity<?> getMonthlyReport(@RequestParam Long id) {
        return ResponseEntity.ok(expenseService.getMonthlyReport(id));
    }

    @GetMapping("/category-report/{category}")
    public ResponseEntity<List<Expense>> getCategoryWiseReport(@PathVariable String category) {
        return ResponseEntity.ok(expenseService.getCategoryWiseReport(category));
    }

}
