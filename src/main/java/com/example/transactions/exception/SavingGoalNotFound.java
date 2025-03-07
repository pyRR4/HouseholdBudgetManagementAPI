package com.example.transactions.exception;

public class SavingGoalNotFound extends NotFoundException {
    public SavingGoalNotFound(Long id) {
        super(String.format("Saving goal with ID: %s not found.", id));
    }
}
