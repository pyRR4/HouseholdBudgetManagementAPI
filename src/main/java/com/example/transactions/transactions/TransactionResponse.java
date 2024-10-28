package com.example.transactions.transactions;

import com.example.transactions.categories.CategoryEntity;
import com.example.transactions.users.UserEntity;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
public class TransactionResponse { //DTO class

    private double transactionValue;
    private LocalDateTime transactionDate;
    private boolean expense;
    private CategoryEntity transactionCategory;
    private String hashCode;
    private UserEntity user;

    public TransactionResponse(double transactionValue, LocalDateTime transactionDate, boolean expense, CategoryEntity transactionCategory, UserEntity user) {
        this.transactionValue = transactionValue;
        this.transactionDate = transactionDate;
        this.expense = expense;
        this.transactionCategory = transactionCategory;
        this.user = user;
    }

    public TransactionResponse(double transactionValue, LocalDateTime transactionDate, boolean expense, CategoryEntity transactionCategory, String hashCode, UserEntity user) {
        this(transactionValue, transactionDate, expense, transactionCategory, user);
        this.hashCode = hashCode;
    }

    @Override
    public String toString() {
        return "TransactionResponse{" +
                "transactionValue=" + transactionValue +
                ", transactionDate=" + transactionDate +
                ", expense=" + expense +
                ", transactionCategory='" + transactionCategory + '\'' +
                ", hashCode='" + hashCode +
                ", user=" + user + '\'' +
                '}';
    }
}


