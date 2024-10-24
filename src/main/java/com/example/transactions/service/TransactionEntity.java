package com.example.transactions.service;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.text.DateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;


@Getter
@Setter
@Entity
public class TransactionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transaction_id;
    private double transactionValue;
    private LocalDateTime transactionDate;
    private boolean expense;
    private String transactionCategory;
    private String hashCode;
    @ManyToOne
    @JoinColumn(name = "username", referencedColumnName = "username")
    private UserEntity user;

    private static final ArrayList<String> categories = new ArrayList<>(List.of("DEFAULT"));

    public TransactionEntity() {}

    public TransactionEntity(double transactionValue, LocalDateTime transactionDate, boolean expense, String transactionCategory) {
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
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return "Transaction{" +
                "id=" + transaction_id +
                ", value=" + transactionValue +
                ", date=" + transactionDate.format(formatter) +
                ", isExpense=" + expense +
                ", category='" + transactionCategory + '\'' +
                '}';
    }

    public static String addCategory(String category) {
        String upperCategory = category.toUpperCase();
        if (!categories.contains(upperCategory)) {
            categories.add(upperCategory);
            return "Added category: " + upperCategory;
        } else {
            return "Failed to add category: " + upperCategory + " already exists.";
        }
    }


    public static String removeCategory(String category) {
        String upperCategory = category.toUpperCase();
        if (categories.contains(upperCategory)) {
            categories.remove(upperCategory);
            return "Removed category: " + upperCategory;
        } else {
            return "Failed to remove category: " + upperCategory + " does not exist.";
        }
    }
}

