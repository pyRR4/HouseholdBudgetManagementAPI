package com.example.transactions.controller;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class TransactionResponse { //DTO class

    private double transactionValue;
    private Date transactionDate;
    private boolean expense;
    private String transactionCategory;
    private String hashCode;

    public TransactionResponse(double transactionValue, Date transactionDate, boolean expense, String transactionCategory) {
        this.transactionValue = transactionValue;
        this.transactionDate = transactionDate;
        this.expense = expense;
        this.transactionCategory = transactionCategory;
    }

    public TransactionResponse(double transactionValue, Date transactionDate, boolean expense, String transactionCategory, String hashCode) {
        this(transactionValue, transactionDate, expense, transactionCategory);
        this.hashCode = hashCode;
    }

    @Override
    public String toString() {
        return "TransactionResponse{" +
                "transactionValue=" + transactionValue +
                ", transactionDate=" + transactionDate +
                ", expense=" + expense +
                ", transactionCategory='" + transactionCategory + '\'' +
                ", hashCode='" + hashCode + '\'' +
                '}';
    }
}


