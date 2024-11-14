package com.example.transactions.transactions;

public class TransactionMapper {
    public static TransactionResponse toResponse(TransactionEntity transactionEntity) {
        return new TransactionResponse(transactionEntity.getTransactionValue(),
                transactionEntity.getTransactionDate(),
                transactionEntity.isExpense(),
                transactionEntity.getCategory(),
                transactionEntity.getHashCode());
    }

    public static TransactionEntity toEntity(TransactionResponse transactionResponse) {
        TransactionEntity transactionEntity = new TransactionEntity();

        transactionEntity.setTransactionValue(transactionResponse.getTransactionValue());
        transactionEntity.setTransactionDate(transactionResponse.getTransactionDate());
        transactionEntity.setExpense(transactionResponse.isExpense());
        transactionEntity.setCategory(transactionResponse.getTransactionCategory());
        transactionEntity.setHashCode(transactionResponse.getHashCode());

        return transactionEntity;
    }
}
