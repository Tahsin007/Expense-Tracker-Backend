package com.project.Expense.Tracker.Controller;

import com.project.Expense.Tracker.Entity.RecurringTransactions;
import com.project.Expense.Tracker.Service.RecurringTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/recurring")
public class RecurringTransactionController {

    @Autowired
    private RecurringTransactionService recurringTransactionService;

    @GetMapping
    public ResponseEntity<List<RecurringTransactions>> getAllRecurringTransactions() {
        return new ResponseEntity<>(recurringTransactionService.getAllRecurringTransactions(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RecurringTransactions> getRecurringTransactionById(@PathVariable Long id) {
        return new ResponseEntity<>(recurringTransactionService.getRecurringTransactionById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<RecurringTransactions> createRecurringTransaction(@RequestBody RecurringTransactions recurringTransaction) {
        return new ResponseEntity<>(recurringTransactionService.createRecurringTransaction(recurringTransaction), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RecurringTransactions> updateRecurringTransaction(@PathVariable Long id, @RequestBody RecurringTransactions recurringTransaction) {
        return new ResponseEntity<>(recurringTransactionService.updateRecurringTransaction(id, recurringTransaction), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRecurringTransaction(@PathVariable Long id) {
        recurringTransactionService.deleteRecurringTransaction(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
