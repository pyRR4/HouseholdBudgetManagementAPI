package com.example.transactions.transactions;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
public class TransactionResponse { //DTO class

    @NotNull(message = "TransactionValue cannot be null")
    @Positive(message = "Amount must be a positive number")
    @Digits(integer = 10, fraction = 2, message = "Amount must be a valid number with up to 2 decimal places")
    private double transactionValue;

    @NotNull(message = "TransactionDate cannot be null")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime transactionDate;

    @NotNull(message = "Transaction isExpense cannot be null")
    private boolean expense;

    private String transactionCategory;

    @NotNull(message = "Transaction hashCode cannot be null")
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


