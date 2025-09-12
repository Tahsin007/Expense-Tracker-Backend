package com.project.Expense.Tracker.Controller;

import com.project.Expense.Tracker.Entity.DTO.RecurringTransactionSummaryDTO;
import com.project.Expense.Tracker.Entity.RecurringTransactions;
import com.project.Expense.Tracker.Service.RecurringTransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/recurring-transactions")
@Tag(name = "Recurring Transaction Controller", description = "APIs for managing recurring transactions")
public class RecurringTransactionController {

    @Autowired
    private RecurringTransactionService recurringTransactionService;

    @PostMapping
    public ResponseEntity<RecurringTransactions> createRecurringTransaction(@RequestBody RecurringTransactions recurringTransaction) {
        return new ResponseEntity<>(recurringTransactionService.createRecurringTransaction(recurringTransaction), HttpStatus.CREATED);
    }
    @GetMapping
    public ResponseEntity<List<RecurringTransactions>> getAllRecurringTransactions() {
        return new ResponseEntity<>(recurringTransactionService.getAllRecurringTransactions(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RecurringTransactions> getRecurringTransactionById(@PathVariable Long id) {
        return new ResponseEntity<>(recurringTransactionService.getRecurringTransactionById(id), HttpStatus.OK);
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

    @GetMapping("/upcoming")
    public ResponseEntity<List<RecurringTransactions>> getUpcomingTransactions() {
        return new ResponseEntity<>(recurringTransactionService.getUpcomingTransactions(), HttpStatus.OK);
    }

    @GetMapping("/overdue")
    public ResponseEntity<List<RecurringTransactions>> getOverdueTransactions() {
        return new ResponseEntity<>(recurringTransactionService.getOverdueTransactions(), HttpStatus.OK);
    }

    @GetMapping("/summary")
    public ResponseEntity<RecurringTransactionSummaryDTO> getRecurringTransactionSummary(
            @RequestParam int year, @RequestParam int month) {
        return new ResponseEntity<>(recurringTransactionService.getRecurringTransactionSummary(year, month), HttpStatus.OK);
    }
}
