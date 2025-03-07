package com.example.transactions.mappers;

import com.example.transactions.dto.CategoryDTO;
import com.example.transactions.dto.TransactionDTO;
import com.example.transactions.dto.UserDTO;
import com.example.transactions.entity.Category;
import com.example.transactions.entity.Transaction;
import com.example.transactions.entity.User;
import com.example.transactions.mapper.TransactionMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class TransactionMapperTest {

    private final TransactionMapper transactionMapper = Mappers.getMapper(TransactionMapper.class);
    private Transaction transaction;
    private TransactionDTO transactionDTO;

    @BeforeEach
    void setUp() {
        User user = new User(1L, "john_doe", "securePass123", "john@example.com", BigDecimal.ZERO, null, null);
        UserDTO userDTO = new UserDTO(user.getId(), user.getUsername(), user.getPassword(), user.getEmail(), 0);

        Category category = new Category(1L, "Food", user, null);
        CategoryDTO categoryDTO = new CategoryDTO(category.getId(), category.getName(), userDTO);

        transaction = new Transaction(1L, new BigDecimal("50.25"), LocalDateTime.now(), category);
        transactionDTO = new TransactionDTO(transaction.getId(), transaction.getAmount().doubleValue(), transaction.getDate(), categoryDTO);
    }

    @Test
    void shouldMapTransactionToDTO() {
        TransactionDTO dto = transactionMapper.toDTO(transaction);

        assertThat(dto).isNotNull();
        assertThat(dto.id()).isEqualTo(transaction.getId());
        assertThat(dto.amount()).isEqualTo(transaction.getAmount().doubleValue());
        assertThat(dto.date()).isEqualTo(transaction.getDate());
        assertThat(dto.categoryDTO().id()).isEqualTo(transaction.getCategory().getId());
    }

    @Test
    void shouldMapTransactionDTOToEntity() {
        Transaction entity = transactionMapper.toEntity(transactionDTO);

        assertThat(entity).isNotNull();
        assertThat(entity.getId()).isEqualTo(transactionDTO.id());
        assertThat(entity.getAmount().doubleValue()).isEqualTo(transactionDTO.amount());
        assertThat(entity.getDate()).isEqualTo(transactionDTO.date());
        assertThat(entity.getCategory().getId()).isEqualTo(transactionDTO.categoryDTO().id());
    }
}
