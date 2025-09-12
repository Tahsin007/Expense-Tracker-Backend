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
    @GetMapping
    public ResponseEntity<?> getAllTransactions(){
        List<Transaction> allExpens = transactionService.getAllTransactions();
        return new ResponseEntity<>(allExpens, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> addTransaction(@RequestBody TransactionRequest request){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Transaction transaction1 = transactionService.saveTransaction(request,authentication.getName());
        return new ResponseEntity<>(transaction1,HttpStatus.CREATED);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTransaction(@PathVariable Long id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return new ResponseEntity<>(transactionService.deleteTransaction(id,authentication.getName()), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<Transaction> updateTransactionById(@RequestParam Long id, @RequestBody TransactionRequest transaction){
        return new ResponseEntity<>(transactionService.updateTransactionById(id, transaction), HttpStatus.OK);
    }
    @GetMapping("/monthly-report")
    public ResponseEntity<?> getMonthlyReport(@RequestParam int month,@RequestParam int year) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.ok(transactionService.getMonthlyTransactions(authentication.getName(),month,year));
    }

    @GetMapping("/yearly-report")
    public ResponseEntity<?> getYearlyReport(@RequestParam int year) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.ok(transactionService.getYearlyTransactions(authentication.getName(),year));
    }

    @GetMapping("/category-report/{category}")
    public ResponseEntity<List<Transaction>> getCategoryWiseReport(@PathVariable String category) {
        return ResponseEntity.ok(transactionService.getCategoryWiseReport(category));
    }

}
