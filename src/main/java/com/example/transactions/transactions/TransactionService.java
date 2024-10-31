package com.example.transactions.transactions;

import com.example.transactions.HashingService;
import com.example.transactions.categories.CategoryEntity;
import com.example.transactions.exceptions.TransactionNotFoundException;
import com.example.transactions.users.UserEntity;
import com.example.transactions.users.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.transactions.transactions.TransactionMapper.toEntity;
import static com.example.transactions.transactions.TransactionMapper.toResponse;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;

    private final UserService userService;
    private final HashingService hashingService;

    @Autowired
    public TransactionService(TransactionRepository transactionRepository, HashingService hashingService, UserService userService) {
        this.transactionRepository = transactionRepository;
        this.hashingService = hashingService;
        this.userService = userService;
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

    public List<TransactionResponse> getTransactionsByCategory(CategoryEntity category) {
        return transactionRepository.findAllByCategory(category).stream()
                .map(TransactionMapper::toResponse)
                .toList();
    }

    public List<TransactionResponse> getTransactionsByUser(String username) {
        return transactionRepository.findAllByUser(userService.getUserByUsername(username)).stream()
                .map(TransactionMapper::toResponse)
                .toList();
    }

    public void deleteTransaction(String hashCode) {
        if(transactionRepository.existsByHashCode(hashCode))
            transactionRepository.deleteByHashCode(hashCode);
        else
            throw new TransactionNotFoundException(hashCode);
    }
}
