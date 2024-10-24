package com.example.transactions.controller;

import com.example.transactions.service.TransactionEntity;

public class TransactionMapper {
    public static TransactionResponse toResponse(TransactionEntity transactionEntity) {
        return new TransactionResponse(transactionEntity.getTransactionValue(),
                transactionEntity.getTransactionDate(),
                transactionEntity.isExpense(),
                transactionEntity.getTransactionCategory(),
                transactionEntity.getHashCode(),
                transactionEntity.getUser());
    }

    public static TransactionEntity toEntity(TransactionResponse transactionResponse) {
        TransactionEntity transactionEntity = new TransactionEntity();

        transactionEntity.setTransactionValue(transactionResponse.getTransactionValue());
        transactionEntity.setTransactionDate(transactionResponse.getTransactionDate());
        transactionEntity.setExpense(transactionResponse.isExpense());
        transactionEntity.setTransactionCategory(transactionResponse.getTransactionCategory());
        transactionEntity.setHashCode(transactionResponse.getHashCode());
        transactionEntity.setUser(transactionResponse.getUser());

        return transactionEntity;
    }
}
