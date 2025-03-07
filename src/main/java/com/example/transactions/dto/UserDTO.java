package com.example.transactions.dto;

public record UserDTO(
        Long id,
        String username,
        String password,
        String email,
        double balance
) {
}
