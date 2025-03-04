package com.example.transactions.exception;

public class CategoryNotFound extends NotFoundException {
    public CategoryNotFound(Long id) {
        super(String.format("Category with ID: %s not found.", id));
    }
}
