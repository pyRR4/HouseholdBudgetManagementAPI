package com.example.transactions.service;

import com.example.transactions.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;

    @Autowired
    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public TransactionEntity updateTransaction(Long id, TransactionEntity transactionEntity) {
        return transactionRepository.findById(id)
                .map(oldTransaction -> {
                    oldTransaction.setTransaction_id(transactionEntity.getTransaction_id());
                    oldTransaction.setTransactionDate(transactionEntity.getTransactionDate());
                    oldTransaction.setTransactionCategory(transactionEntity.getTransactionCategory());
                    oldTransaction.setTransactionValue(transactionEntity.getTransactionValue());
                    oldTransaction.setExpense(transactionEntity.isExpense());
                    return transactionRepository.save(oldTransaction);
                }).orElseGet(() -> {
                    transactionEntity.setTransaction_id(id);
                    return transactionRepository.save(transactionEntity);
                });
    }
}
