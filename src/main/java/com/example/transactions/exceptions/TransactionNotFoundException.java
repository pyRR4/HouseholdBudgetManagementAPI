package com.example.transactions.exceptions;

public class TransactionNotFoundException extends RuntimeException {
    public TransactionNotFoundException(String hashCode) {
        super("Could not find transaction with hashCode " + hashCode);
    }
}
