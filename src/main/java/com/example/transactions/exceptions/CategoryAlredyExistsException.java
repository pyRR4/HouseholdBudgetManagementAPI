package com.example.transactions.exceptions;

public class CategoryAlredyExistsException extends RuntimeException {
    public CategoryAlredyExistsException(String category) {
        super("Category with name " + category + " already exists");
    }
}
