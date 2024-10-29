package com.example.transactions.exceptions;


import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({
            TransactionNotFoundException.class,
            UserNotFoundException.class,
            CategoryNotFoundException.class
    })
    public ResponseEntity<String> handleTransactionNotFoundException(RuntimeException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler({
            UserAlreadyExistsException.class,
            DataIntegrityViolationException.class
    })
    public ResponseEntity<String> handleUserAlreadyExists(RuntimeException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    }
}
