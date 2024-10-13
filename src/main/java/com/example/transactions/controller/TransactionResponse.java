package com.example.transactions.controller;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class TransactionResponse {
    private double transactionValue;
    private Date transactionDate;
    private boolean expense;
    private String transactionCategory;

    public TransactionResponse(double transactionValue, Date transactionDate, boolean expense, String transactionCategory) {
        this.transactionValue = transactionValue;
        this.transactionDate = transactionDate;
        this.expense = expense;
        this.transactionCategory = transactionCategory;
    }
}


