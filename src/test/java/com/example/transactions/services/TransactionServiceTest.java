package com.example.transactions.services;

import com.example.transactions.dto.TransactionDTO;
import com.example.transactions.entity.Transaction;
import com.example.transactions.exception.TransactionNotFound;
import com.example.transactions.mapper.TransactionMapper;
import com.example.transactions.repository.TransactionRepository;
import com.example.transactions.service.contract.implementation.TransactionServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private TransactionMapper transactionMapper;

    @InjectMocks
    private TransactionServiceImpl transactionService;

    private Transaction transaction;
    private TransactionDTO transactionDTO;

    @BeforeEach
    void setUp() {
        transaction = new Transaction(1L, new BigDecimal("100.00"), LocalDateTime.now(), null);
        transactionDTO = new TransactionDTO(transaction.getId(), transaction.getAmount().doubleValue(), transaction.getDate(), null);
    }

    @Test
    void shouldCreateTransaction() {
        when(transactionMapper.toEntity(any(TransactionDTO.class))).thenReturn(transaction);
        when(transactionRepository.save(any(Transaction.class))).thenReturn(transaction);
        when(transactionMapper.toDTO(any(Transaction.class))).thenReturn(transactionDTO);

        TransactionDTO created = transactionService.create(transactionDTO);

        assertThat(created).isNotNull();
        assertThat(created.id()).isEqualTo(transactionDTO.id());
        verify(transactionRepository, times(1)).save(transaction);
    }

    @Test
    void shouldDeleteTransaction_WhenExists() {
        when(transactionRepository.findById(transaction.getId())).thenReturn(Optional.of(transaction));

        transactionService.delete(transaction.getId());

        verify(transactionRepository, times(1)).delete(transaction);
    }

    @Test
    void shouldThrowException_WhenDeletingNonExistingTransaction() {
        when(transactionRepository.findById(transaction.getId())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> transactionService.delete(transaction.getId()))
                .isInstanceOf(TransactionNotFound.class)
                .hasMessageContaining(String.format("Transaction with ID: %s not found.", transaction.getId()));
    }

    @Test
    void shouldGetTransactionById_WhenExists() {
        when(transactionRepository.findById(transaction.getId())).thenReturn(Optional.of(transaction));
        when(transactionMapper.toDTO(transaction)).thenReturn(transactionDTO);

        TransactionDTO found = transactionService.getById(transaction.getId());

        assertThat(found).isNotNull();
        assertThat(found.id()).isEqualTo(transactionDTO.id());
    }

    @Test
    void shouldThrowException_WhenGettingNonExistingTransaction() {
        when(transactionRepository.findById(transaction.getId())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> transactionService.getById(transaction.getId()))
                .isInstanceOf(TransactionNotFound.class)
                .hasMessageContaining(String.format("Transaction with ID: %s not found.", transaction.getId()));
    }

    @Test
    void shouldGetAllTransactions() {
        List<Transaction> transactions = List.of(transaction);
        List<TransactionDTO> transactionDTOs = List.of(transactionDTO);

        when(transactionRepository.findAll()).thenReturn(transactions);
        when(transactionMapper.toDTO(transaction)).thenReturn(transactionDTO);

        List<TransactionDTO> found = transactionService.getAll();

        assertThat(found).hasSize(1);
        assertThat(found.get(0).id()).isEqualTo(transactionDTO.id());
    }
}
