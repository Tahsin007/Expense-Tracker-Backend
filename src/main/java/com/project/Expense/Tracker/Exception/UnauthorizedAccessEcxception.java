package com.project.Expense.Tracker.Exception;

public class UnauthorizedAccessEcxception extends RuntimeException{
    public UnauthorizedAccessEcxception(String message){
        super(message);
    }
}
