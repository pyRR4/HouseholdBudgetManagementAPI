package com.example.transactions.exceptions;

public class CategoryNotFound extends RuntimeException {
    public CategoryNotFound(Long id) {
        super(String.format("Category with ID: %s not found.", id));
    }
}
