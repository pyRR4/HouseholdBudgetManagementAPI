package com.example.transactions.exceptions;

public class TransactionNotFound extends RuntimeException {
    public TransactionNotFound(Long id) {
        super(String.format("Transaction with ID: %s not found.", id));
    }
}
