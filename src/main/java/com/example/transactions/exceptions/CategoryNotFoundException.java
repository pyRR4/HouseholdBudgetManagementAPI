package com.example.transactions.exceptions;

public class CategoryNotFoundException extends RuntimeException {
    public CategoryNotFoundException(String category) {
        super("Could not find category with name " + category);
    }
}
