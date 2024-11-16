package com.example.transactions.transactions;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
public class TransactionResponse { //DTO class

    private double transactionValue;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime transactionDate;
    private boolean expense;
    private String transactionCategory;
    private String hashCode;

    public TransactionResponse(double transactionValue, LocalDateTime transactionDate, boolean expense, String transactionCategory) {
        this.transactionValue = transactionValue;
        this.transactionDate = transactionDate;
        this.expense = expense;
        this.transactionCategory = transactionCategory;
    }

    public TransactionResponse(double transactionValue, LocalDateTime transactionDate, boolean expense, String transactionCategory, String hashCode) {
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
                ", hashCode='" + hashCode +
                '}';
    }
}


