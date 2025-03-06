package com.example.transactions.dto;

public record SavingGoalDTO(
        Long id,
        String title,
        double amount,
        double currentAmount,
        UserDTO userDTO
) {
}
