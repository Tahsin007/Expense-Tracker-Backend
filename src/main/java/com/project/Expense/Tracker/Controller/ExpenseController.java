package com.project.Expense.Tracker.Controller;

import com.project.Expense.Tracker.Entity.Expense;
import com.project.Expense.Tracker.Repository.ExpenseRepository;
import com.project.Expense.Tracker.Service.ExpenseService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/expenses")
public class ExpenseController {
    @Autowired
    private ExpenseService expenseService;

    @GetMapping
    public ResponseEntity<?> getAllExpenses(){
        List<Expense> allExpenses = expenseService.getAllExpenses();
        return new ResponseEntity<>(allExpenses, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Expense> addExpense(@RequestBody Expense  expense){
        Expense expense1 = expenseService.saveExpense(expense);
        return new ResponseEntity<>(expense1,HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public boolean deleteExpense(@PathVariable Long id){
        expenseService.deleteAnExpense(id);
        return true;
    }

    @PutMapping("/{id}")
    public Expense updateExpenseById(@PathVariable Long id, @RequestBody Expense expense){
        return expenseService.updateExpenseById(id,expense);
    }

    @GetMapping("/monthly-report")
    public ResponseEntity<List<Expense>> getMonthlyReport() {
        return ResponseEntity.ok(expenseService.getMonthlyReport());
    }

    @GetMapping("/category-report/{category}")
    public ResponseEntity<List<Expense>> getCategoryWiseReport(@PathVariable String category) {
        return ResponseEntity.ok(expenseService.getCategoryWiseReport(category));
    }

}
