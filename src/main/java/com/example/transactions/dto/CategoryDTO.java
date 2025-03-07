package com.example.transactions.dto;

public record CategoryDTO(
        Long id,
        String name,
        UserDTO userDTO
) {
}
