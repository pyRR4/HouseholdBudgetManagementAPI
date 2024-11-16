package com.example.transactions.transactions;


import com.example.transactions.categories.CategoryService;
import org.springframework.stereotype.Component;

@Component
public class TransactionMapper {

    private final CategoryService categoryService;


    public TransactionMapper(CategoryService categoryService) {
        this.categoryService =  categoryService;
    }

    public static TransactionResponse toResponse(TransactionEntity transactionEntity) {
        return new TransactionResponse(transactionEntity.getTransactionValue(),
                transactionEntity.getTransactionDate(),
                transactionEntity.isExpense(),
                transactionEntity.getCategory().getName(),
                transactionEntity.getHashCode());
    }

    public TransactionEntity toEntity(TransactionResponse transactionResponse, String username) {
        TransactionEntity transactionEntity = new TransactionEntity();

        transactionEntity.setTransactionValue(transactionResponse.getTransactionValue());
        transactionEntity.setTransactionDate(transactionResponse.getTransactionDate());
        transactionEntity.setExpense(transactionResponse.isExpense());
        transactionEntity.setCategory(categoryService.getCategoryByUsernameAndName(username, transactionResponse.getTransactionCategory()));
        transactionEntity.setHashCode(transactionResponse.getHashCode());

        return transactionEntity;
    }
}
