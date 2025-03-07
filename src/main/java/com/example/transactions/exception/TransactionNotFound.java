package com.example.transactions.exception;

public class TransactionNotFound extends NotFoundException {
    public TransactionNotFound(Long id) {
        super(String.format("Transaction with ID: %s not found.", id));
    }
}
