package com.project.Expense.Tracker.Controller;

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
@RequestMapping("/recurring")
@Tag(name = "Recurring Transaction Controller", description = "APIs for managing recurring transactions")
public class RecurringTransactionController {

    @Autowired
    private RecurringTransactionService recurringTransactionService;

    @Operation(
            description = "Get all recurring transactions.",
            summary = "Get All Recurring Transactions",
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200")
            }
    )
    @GetMapping
    public ResponseEntity<List<RecurringTransactions>> getAllRecurringTransactions() {
        return new ResponseEntity<>(recurringTransactionService.getAllRecurringTransactions(), HttpStatus.OK);
    }

    @Operation(
            description = "Get a recurring transaction by its ID.",
            summary = "Get Recurring Transaction by ID",
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200")
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<RecurringTransactions> getRecurringTransactionById(@PathVariable Long id) {
        return new ResponseEntity<>(recurringTransactionService.getRecurringTransactionById(id), HttpStatus.OK);
    }

    @Operation(
            description = "Create a new recurring transaction.",
            summary = "Create Recurring Transaction",
            responses = {
                    @ApiResponse(description = "Recurring transaction created successfully", responseCode = "201")
            }
    )
    @PostMapping
    public ResponseEntity<RecurringTransactions> createRecurringTransaction(@RequestBody RecurringTransactions recurringTransaction) {
        return new ResponseEntity<>(recurringTransactionService.createRecurringTransaction(recurringTransaction), HttpStatus.CREATED);
    }

    @Operation(
            description = "Update an existing recurring transaction.",
            summary = "Update Recurring Transaction",
            responses = {
                    @ApiResponse(description = "Recurring transaction updated successfully", responseCode = "200")
            }
    )
    @PutMapping("/{id}")
    public ResponseEntity<RecurringTransactions> updateRecurringTransaction(@PathVariable Long id, @RequestBody RecurringTransactions recurringTransaction) {
        return new ResponseEntity<>(recurringTransactionService.updateRecurringTransaction(id, recurringTransaction), HttpStatus.OK);
    }

    @Operation(
            description = "Delete a recurring transaction by its ID.",
            summary = "Delete Recurring Transaction",
            responses = {
                    @ApiResponse(description = "Recurring transaction deleted successfully", responseCode = "204")
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRecurringTransaction(@PathVariable Long id) {
        recurringTransactionService.deleteRecurringTransaction(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
