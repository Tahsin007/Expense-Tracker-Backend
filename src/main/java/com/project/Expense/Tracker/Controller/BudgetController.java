package com.project.Expense.Tracker.Controller;


import com.project.Expense.Tracker.Entity.Budgets;
import com.project.Expense.Tracker.Entity.Transaction;
import com.project.Expense.Tracker.Service.BudgetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/budget")
public class BudgetController {
    @Autowired
    private BudgetService budgetService;

    @PostMapping
    public ResponseEntity<?> setMonthlyBudget(@RequestBody Budgets budgets){
        try{
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            Budgets budget = budgetService.setMonthlyBudget(authentication.getName(),budgets);
            return new ResponseEntity<>(budget, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
        }

    }

}
