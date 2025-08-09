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

    @Operation(
            description = "Create a new recurring transaction",
            summary = "Create a new recurring transaction",
            responses = {
                    @ApiResponse(description = "Success", responseCode = "201")
            }
    )
    @PostMapping
    public ResponseEntity<RecurringTransactions> createRecurringTransaction(@RequestBody RecurringTransactions recurringTransaction) {
        return new ResponseEntity<>(recurringTransactionService.createRecurringTransaction(recurringTransaction), HttpStatus.CREATED);
    }

    @Operation(
            description = "Get all recurring transactions for logged-in user",
            summary = "Get all recurring transactions for logged-in user",
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200")
            }
    )
    @GetMapping
    public ResponseEntity<List<RecurringTransactions>> getAllRecurringTransactions() {
        return new ResponseEntity<>(recurringTransactionService.getAllRecurringTransactions(), HttpStatus.OK);
    }

    @Operation(
            description = "Get details of a specific recurring transaction",
            summary = "Get details of a specific recurring transaction",
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200")
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<RecurringTransactions> getRecurringTransactionById(@PathVariable Long id) {
        return new ResponseEntity<>(recurringTransactionService.getRecurringTransactionById(id), HttpStatus.OK);
    }

    @Operation(
            description = "Update a recurring transaction",
            summary = "Update a recurring transaction",
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200")
            }
    )
    @PutMapping("/{id}")
    public ResponseEntity<RecurringTransactions> updateRecurringTransaction(@PathVariable Long id, @RequestBody RecurringTransactions recurringTransaction) {
        return new ResponseEntity<>(recurringTransactionService.updateRecurringTransaction(id, recurringTransaction), HttpStatus.OK);
    }

    @Operation(
            description = "Delete a recurring transaction",
            summary = "Delete a recurring transaction",
            responses = {
                    @ApiResponse(description = "Success", responseCode = "204")
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRecurringTransaction(@PathVariable Long id) {
        recurringTransactionService.deleteRecurringTransaction(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(
            description = "Get upcoming payments (next_occurrence in future)",
            summary = "Get upcoming payments",
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200")
            }
    )
    @GetMapping("/upcoming")
    public ResponseEntity<List<RecurringTransactions>> getUpcomingTransactions() {
        return new ResponseEntity<>(recurringTransactionService.getUpcomingTransactions(), HttpStatus.OK);
    }

    @Operation(
            description = "Get overdue payments (next_occurrence < today & still active)",
            summary = "Get overdue payments",
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200")
            }
    )
    @GetMapping("/overdue")
    public ResponseEntity<List<RecurringTransactions>> getOverdueTransactions() {
        return new ResponseEntity<>(recurringTransactionService.getOverdueTransactions(), HttpStatus.OK);
    }

    @Operation(
            description = "Get recurring transaction summary for a given month and year",
            summary = "Get recurring transaction summary",
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200")
            }
    )
    @GetMapping("/summary")
    public ResponseEntity<RecurringTransactionSummaryDTO> getRecurringTransactionSummary(
            @RequestParam int year, @RequestParam int month) {
        return new ResponseEntity<>(recurringTransactionService.getRecurringTransactionSummary(year, month), HttpStatus.OK);
    }
}
