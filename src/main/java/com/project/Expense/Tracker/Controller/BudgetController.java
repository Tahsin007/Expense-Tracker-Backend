package com.project.Expense.Tracker.Controller;


import com.project.Expense.Tracker.Entity.Budgets;
import com.project.Expense.Tracker.Entity.Transaction;
import com.project.Expense.Tracker.Service.BudgetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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

    @GetMapping
    public ResponseEntity<?> getAllBudgets(){
        try{
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            List<Budgets> allBudgets = budgetService.getAllBudgets(authentication.getName());
            return new ResponseEntity<>(allBudgets,HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.toString(),HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/month/{month}")
    public ResponseEntity<?> getBudgetsByMonth(@PathVariable int month){
        try{
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            Optional<Budgets> allBudgets = budgetService.getBudgetByMonth(authentication.getName(),month);
            if(allBudgets.isPresent()){
                return new ResponseEntity<>(allBudgets,HttpStatus.OK);
            }else{
                return new ResponseEntity<>("No Budgets are present",HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(e.toString(),HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/year/{year}")
    public ResponseEntity<?> getBudgetByYear(@PathVariable int year){
        try{
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            Optional<List<Budgets>> allBudgets = budgetService.getBudgetsByYear(authentication.getName(),year);
            if(allBudgets.isPresent()){
                return new ResponseEntity<>(allBudgets,HttpStatus.OK);
            }else{
                return new ResponseEntity<>("No Budgets are present in "+year,HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(e.toString(),HttpStatus.BAD_REQUEST);
        }
    }


    @PutMapping("/{budgetId}")
    public ResponseEntity<?> updateBudgetById(@PathVariable Long budgetId,@RequestBody Budgets updatedBudget){
        try{
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            Budgets budgets = budgetService.updateBudget(budgetId, updatedBudget, authentication.getName());
            return new ResponseEntity<>(budgets,HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.toString(),HttpStatus.BAD_REQUEST);
        }
    }


}
