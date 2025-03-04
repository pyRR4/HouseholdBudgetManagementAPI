package com.example.transactions.exception;

public class UserNotFound extends NotFoundException {
    public UserNotFound(String username) {
        super(String.format("User with username: %s not found.", username));
    }

    public UserNotFound(Long id) {
        super(String.format("User with ID: %s not found.", id));
    }
}
