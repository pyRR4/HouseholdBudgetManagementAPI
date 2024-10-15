package com.example.transactions.service;

import com.example.transactions.controller.TransactionMapper;
import com.example.transactions.controller.TransactionResponse;
import com.example.transactions.exceptions.TransactionNotFoundException;
import com.example.transactions.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.transactions.controller.TransactionMapper.toResponse;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;

    @Autowired
    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public List<TransactionResponse> getAllTransactions() {
        return transactionRepository.findAll().stream()
                .map(TransactionMapper::toResponse)
                .toList();
    }

    public TransactionResponse updateTransaction(Long id, TransactionEntity transactionEntity) {
        return toResponse(transactionRepository.findById(id)
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
                }));
    }

    public TransactionEntity createTransaction(TransactionEntity transactionEntity) {
        //obliczanie salda konta usera
        return transactionRepository.save(transactionEntity);
    }

    public TransactionResponse getTransaction(Long id) {
        TransactionEntity transactionEntity = transactionRepository.findById(id)
                .orElseThrow(() -> new TransactionNotFoundException(id));

        return toResponse(transactionEntity);
    }

    public void deleteTransaction(Long id) {
        if(transactionRepository.existsById(id))
            transactionRepository.deleteById(id);
        else
            throw new TransactionNotFoundException(id);
    }
}
