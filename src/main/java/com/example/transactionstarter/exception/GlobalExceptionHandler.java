package com.example.transactionstarter.exception;

import org.springframework.boot.autoconfigure.graphql.GraphQlProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(TransactionNotFoundException.class)
    public ResponseEntity<?> handleTransactionNotFound(TransactionNotFoundException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of(
                        "timestamp", LocalDateTime.now(),
                        "status",404,
                        "error","Not Found",
                        "message",ex.getMessage()
                ));
    }

    @ExceptionHandler(DuplicateTransactionException.class)
    public ResponseEntity<?> handleDuplicateTransaction(DuplicateTransactionException ex){
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Map.of(
                        "timestamp",LocalDateTime.now(),
                        "status",409,
                        "error","Conflict",
                        "message",ex.getMessage()

                ));
    }

    @ExceptionHandler(ValidateException.class)
    public ResponseEntity<?> handleValidationException(ValidateException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of(
                        "timestamp",LocalDateTime.now(),
                        "status",400,
                        "error","Bad Request",
                        "message",ex.getMessage()
                ));
    }

}
