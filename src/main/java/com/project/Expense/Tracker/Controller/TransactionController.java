package com.project.Expense.Tracker.Controller;

import com.project.Expense.Tracker.Entity.DTO.TransactionRequest;
import com.project.Expense.Tracker.Entity.Transaction;
import com.project.Expense.Tracker.Service.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transaction")
@Tag(name = "Transaction Controller", description = "APIs for managing transactions")
public class TransactionController {
    @Autowired
    private TransactionService transactionService;


    @Operation(
            description = "Get all transactions for the authenticated user.",
            summary = "Get All Transactions",
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200"),
                    @ApiResponse(description = "Bad request", responseCode = "400")
            }
    )
    @GetMapping
    public ResponseEntity<?> getAllTransactions(){
        try{
            List<Transaction> allExpens = transactionService.getAllTransactions();
            return new ResponseEntity<>(allExpens, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.toString(), HttpStatus.BAD_REQUEST);
        }

    }

    @Operation(
            description = "Add a new transaction for the authenticated user.",
            summary = "Add Transaction",
            responses = {
                    @ApiResponse(description = "Transaction added successfully", responseCode = "201"),
                    @ApiResponse(description = "Bad request", responseCode = "400")
            }
    )
    @PostMapping
    public ResponseEntity<?> addTransaction(@RequestBody TransactionRequest request){
        try{
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            Transaction transaction1 = transactionService.saveTransaction(request,authentication.getName());
            return new ResponseEntity<>(transaction1,HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.toString(),HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(
            description = "Delete a transaction by its ID for the authenticated user.",
            summary = "Delete Transaction",
            responses = {
                    @ApiResponse(description = "Transaction deleted successfully", responseCode = "200"),
                    @ApiResponse(description = "Bad request", responseCode = "400")
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTransaction(@PathVariable Long id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return new ResponseEntity<>(transactionService.deleteTransaction(id,authentication.getName()), HttpStatus.OK);
    }

    @Operation(
            description = "Update a transaction by its ID.",
            summary = "Update Transaction by ID",
            responses = {
                    @ApiResponse(description = "Transaction updated successfully", responseCode = "200")
            }
    )
    @PutMapping
    public ResponseEntity<Transaction> updateTransactionById(@RequestParam Long id, @RequestBody TransactionRequest transaction){
        return new ResponseEntity<>(transactionService.updateTransactionById(id, transaction), HttpStatus.OK);
    }

    @Operation(
            description = "Get a monthly transaction report for the authenticated user.",
            summary = "Get Monthly Report",
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200")
            }
    )
    @GetMapping("/monthly-report")
    public ResponseEntity<?> getMonthlyReport(@RequestParam int month,@RequestParam int year) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.ok(transactionService.getMonthlyTransactions(authentication.getName(),month,year));
    }

    @Operation(
            description = "Get a yearly transaction report for the authenticated user.",
            summary = "Get Yearly Report",
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200")
            }
    )
    @GetMapping("/yearly-report")
    public ResponseEntity<?> getYearlyReport(@RequestParam int year) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.ok(transactionService.getYearlyTransactions(authentication.getName(),year));
    }

    @Operation(
            description = "Get a transaction report based on category.",
            summary = "Get Category-wise Report",
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200")
            }
    )
    @GetMapping("/category-report/{category}")
    public ResponseEntity<List<Transaction>> getCategoryWiseReport(@PathVariable String category) {

        return ResponseEntity.ok(transactionService.getCategoryWiseReport(category));
    }

}
