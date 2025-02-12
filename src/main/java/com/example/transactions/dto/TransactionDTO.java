package com.example.transactions.dto;

import java.time.LocalDateTime;

public record TransactionDTO(
        Long id,
        double amount,
        LocalDateTime date,
        CategoryDTO categoryDTO
) {
}
