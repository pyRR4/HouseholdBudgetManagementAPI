package com.example.transactions.exceptions;

public class UserNotFound extends RuntimeException {
    public UserNotFound(String username) {
        super(String.format("User with username: %s not found.", username));
    }

    public UserNotFound(Long id) {
        super(String.format("User with ID: %s not found.", id));
    }
}
