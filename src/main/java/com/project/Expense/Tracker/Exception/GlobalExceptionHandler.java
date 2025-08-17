package com.project.Expense.Tracker.Exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleInvalid(ResourceNotFoundException ex) {
        log.warn(ex.getMessage());
        ApiErrorResponse response = new ApiErrorResponse(ex.getMessage(),"Not Found",LocalDateTime.now(),HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RequestBodyException.class)
    public ResponseEntity<ApiErrorResponse> handleRequestInvalid(RequestBodyException ex){
        log.info(ex.getMessage());
        ApiErrorResponse response = new ApiErrorResponse(ex.getMessage(),"Request Body Exception",LocalDateTime.now(),HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(UnauthorizedAccessEcxception.class)
    public ResponseEntity<ApiErrorResponse> handleUnauthorizedAccess(UnauthorizedAccessEcxception ex){
        log.error(ex.getMessage());
        ApiErrorResponse response = new ApiErrorResponse(ex.getMessage(),"Unauthorized",LocalDateTime.now(),HttpStatus.UNAUTHORIZED);
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);

    }

    // Fallback for Any Other Exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleGeneral(Exception ex) {
        log.error(ex.getMessage());
        ApiErrorResponse response = new ApiErrorResponse(
                ex.getMessage(),
                "Internal Server Error: ",
                LocalDateTime.now(),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
