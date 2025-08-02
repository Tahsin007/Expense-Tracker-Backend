package com.project.Expense.Tracker.Controller;


import com.project.Expense.Tracker.Entity.Budgets;
import com.project.Expense.Tracker.Service.BudgetService;
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
import java.util.Optional;

@RestController
@RequestMapping("/budget")
@Tag(name = "Budget Controller", description = "APIs for managing budgets")
public class BudgetController {
    @Autowired
    private BudgetService budgetService;

    @Operation(
            description = "Set a monthly budget for the authenticated user.",
            summary = "Set Monthly Budget",
            responses = {
                    @ApiResponse(description = "Budget created successfully", responseCode = "201"),
                    @ApiResponse(description = "Bad request", responseCode = "400")
            }
    )
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

    @Operation(
            description = "Get all budgets for the authenticated user.",
            summary = "Get All Budgets",
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200"),
                    @ApiResponse(description = "Bad request", responseCode = "400")
            }
    )
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

    @Operation(
            description = "Get budget for a specific month for the authenticated user.",
            summary = "Get Budget by Month",
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200"),
                    @ApiResponse(description = "No budgets found for the given month", responseCode = "400")
            }
    )
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
    @Operation(
            description = "Get all budgets for a specific year for the authenticated user.",
            summary = "Get Budgets by Year",
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200"),
                    @ApiResponse(description = "No budgets found for the given year", responseCode = "400")
            }
    )
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


    @Operation(
            description = "Update a budget by its ID for the authenticated user.",
            summary = "Update Budget by ID",
            responses = {
                    @ApiResponse(description = "Budget updated successfully", responseCode = "200"),
                    @ApiResponse(description = "Bad request", responseCode = "400")
            }
    )
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
