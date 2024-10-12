package com.example.transactions.service;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.text.DateFormat;
import java.util.*;


@Getter
@Setter
@Entity
public class TransactionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transaction_id;
    private double transactionValue;
    private Date transactionDate;
    private boolean expense;
    private String transactionCategory;

    private static final ArrayList<String> categories = new ArrayList<>(List.of("DEFAULT"));

    public TransactionEntity() {}

    public TransactionEntity(double transactionValue, Date transactionDate, boolean expense, String transactionCategory) {
        this.transactionValue = transactionValue;
        this.transactionDate = transactionDate;
        this.expense = expense;
        String upperCaseCategory = transactionCategory.toUpperCase();
        if(categories.contains(upperCaseCategory)) {
            this.transactionCategory = upperCaseCategory;
        } else {
            this.transactionCategory = "DEFAULT";
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TransactionEntity that)) return false;
        return Objects.equals(transaction_id, that.transaction_id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(transaction_id);
    }

    @Override
    public String toString() {
        DateFormat df = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG);
        return "Transaction{" +
                "id=" + transaction_id +
                ", value=" + transactionValue +
                ", date=" + df.format(transactionDate) +
                ", isExpense=" + expense +
                ", category='" + transactionCategory + '\'' +
                '}';
    }

    public static void addCategory(String category) {
        if(!categories.contains(category)) {
            categories.add(category.toUpperCase());
        } else {
            //??
        }
    }

    public static void removeCategory(String category) {
        if(categories.contains(category)) {
            categories.remove(category.toUpperCase());
        } else {
            //??
        }
    }
}

