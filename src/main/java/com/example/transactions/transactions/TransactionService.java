package com.example.transactions.transactions;

import com.example.transactions.HashingService;
import com.example.transactions.categories.CategoryService;
import com.example.transactions.exceptions.TransactionNotFoundException;
import com.example.transactions.users.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.example.transactions.transactions.TransactionMapper.toResponse;

@Service
@Transactional
public class TransactionService {

    private final TransactionRepository transactionRepository;

    private final UserService userService;
    private final CategoryService categoryService;

    private final HashingService hashingService;
    private final TransactionMapper transactionMapper;

    @Autowired
    public TransactionService(
            TransactionRepository transactionRepository,
            HashingService hashingService,
            UserService userService,
            CategoryService categoryService,
            TransactionMapper transactionMapper
    ) {
        this.transactionRepository = transactionRepository;
        this.hashingService = hashingService;
        this.userService = userService;
        this.categoryService = categoryService;
        this.transactionMapper = transactionMapper;
    }

    public TransactionResponse updateTransaction(String username, String hashCode, TransactionResponse transactionResponse) {
        return toResponse(transactionRepository.findByUserUsernameAndHashCode(username, hashCode)
                .map(oldTransaction -> {
                    oldTransaction.setTransactionDate(transactionResponse.getTransactionDate());
                    oldTransaction.setCategory(categoryService.getCategoryByUsernameAndName(username, transactionResponse.getTransactionCategory()));
                    oldTransaction.setTransactionValue(transactionResponse.getTransactionValue());
                    oldTransaction.setExpense(transactionResponse.isExpense());
                    return transactionRepository.save(oldTransaction);
                }).orElseThrow(() -> new TransactionNotFoundException(hashCode)));
    }

    public TransactionResponse createTransaction(String username, TransactionResponse transactionResponse) {
        //obliczanie salda konta usera
        System.out.println(transactionResponse);
        TransactionEntity transactionEntity = transactionMapper.toEntity(transactionResponse, username);
        System.out.println(transactionEntity);
        transactionEntity.setUser(userService.getUserByUsername(username));
        transactionEntity.setHashCode(hashingService.hash(transactionEntity.toString()));
        transactionResponse.setHashCode(transactionEntity.getHashCode());
        transactionRepository.save(transactionEntity);

        return transactionResponse;
    }

    public TransactionResponse getTransaction(String username, String hashCode) {
        TransactionEntity transactionEntity = transactionRepository.findByUserUsernameAndHashCode(username, hashCode)
                .orElseThrow(() -> new TransactionNotFoundException(hashCode));

        return toResponse(transactionEntity);
    }

    public List<TransactionResponse> getTransactionsByUserAndCategory(String username, String category) {
        return transactionRepository.findAllByCategoryNameAndUserUsername(category, username).stream()
                .map(TransactionMapper::toResponse)
                .toList();
    }

    public List<TransactionResponse> getTransactionsByUser(String username) {
        return transactionRepository.findAllByUserUsername(username).stream()
                .map(TransactionMapper::toResponse)
                .toList();
    }

    public void deleteTransaction(String username, String hashCode) {
        if(transactionRepository.existsByUserUsernameAndHashCode(username, hashCode))
            transactionRepository.deleteByUserUsernameAndHashCode(username, hashCode);
        else
            throw new TransactionNotFoundException(hashCode);
    }
}
