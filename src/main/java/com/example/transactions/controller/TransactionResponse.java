package com.example.transactions.controller;

import com.example.transactions.service.UserEntity;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
public class TransactionResponse { //DTO class

    private double transactionValue;
    private LocalDateTime transactionDate;
    private boolean expense;
    private String transactionCategory;
    private String hashCode;
    private UserEntity user;

    public TransactionResponse(double transactionValue, LocalDateTime transactionDate, boolean expense, String transactionCategory, UserEntity user) {
        this.transactionValue = transactionValue;
        this.transactionDate = transactionDate;
        this.expense = expense;
        this.transactionCategory = transactionCategory;
        this.user = user;
    }

    public TransactionResponse(double transactionValue, LocalDateTime transactionDate, boolean expense, String transactionCategory, String hashCode, UserEntity user) {
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


