package com.project.Expense.Tracker.Exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
@Setter
public class ApiErrorResponse {

    private final HttpStatus status;
    private LocalDateTime timestamp;
    private String error;
    private String message;

    public ApiErrorResponse(String message,String error,LocalDateTime localDateTime, HttpStatus status) {
        this.status = status;
        this.error=error;
        this.timestamp = localDateTime;
        this.message = message;
    }

}
