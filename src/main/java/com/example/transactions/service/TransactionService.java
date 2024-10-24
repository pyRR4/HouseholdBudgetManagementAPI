package com.example.transactions.service;

import com.example.transactions.controller.TransactionMapper;
import com.example.transactions.controller.TransactionResponse;
import com.example.transactions.exceptions.TransactionNotFoundException;
import com.example.transactions.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.transactions.controller.TransactionMapper.toEntity;
import static com.example.transactions.controller.TransactionMapper.toResponse;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final HashingService hashingService;

    @Autowired
    public TransactionService(TransactionRepository transactionRepository, HashingService hashingService) {
        this.transactionRepository = transactionRepository;
        this.hashingService = hashingService;
    }

    public List<TransactionResponse> getAllTransactions() {
        return transactionRepository.findAll().stream()
                .map(TransactionMapper::toResponse)
                .toList();
    }

    public TransactionResponse updateTransaction(String hashCode, TransactionResponse transactionResponse) {
        return toResponse(transactionRepository.findByHashCode(hashCode)
                .map(oldTransaction -> {
                    oldTransaction.setTransactionDate(transactionResponse.getTransactionDate());
                    oldTransaction.setTransactionCategory(transactionResponse.getTransactionCategory());
                    oldTransaction.setTransactionValue(transactionResponse.getTransactionValue());
                    oldTransaction.setExpense(transactionResponse.isExpense());
                    return transactionRepository.save(oldTransaction);
                }).orElseThrow(() -> new TransactionNotFoundException(hashCode)));
    }

    public TransactionResponse createTransaction(TransactionResponse transactionResponse) {
        //obliczanie salda konta usera
        TransactionEntity transactionEntity = toEntity(transactionResponse);
        transactionEntity.setHashCode(hashingService.hash(transactionEntity.toString()));
        transactionRepository.save(transactionEntity);

        return transactionResponse;
    }

    public TransactionResponse getTransaction(String hashCode) {
        TransactionEntity transactionEntity = transactionRepository.findByHashCode(hashCode)
                .orElseThrow(() -> new TransactionNotFoundException(hashCode));

        return toResponse(transactionEntity);
    }

    public void deleteTransaction(String hashCode) {
        if(transactionRepository.existsByHashCode(hashCode))
            transactionRepository.deleteByHashCode(hashCode);
        else
            throw new TransactionNotFoundException(hashCode);
    }
}
