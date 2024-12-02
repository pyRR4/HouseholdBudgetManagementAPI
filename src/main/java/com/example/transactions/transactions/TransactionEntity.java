package com.example.transactions.transactions;

import com.example.transactions.categories.CategoryEntity;
import com.example.transactions.users.UserEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;


@Getter
@Setter
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"hashCode"}))
public class TransactionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transactionId;

    @NotNull(message = "TransactionValue cannot be null")
    @Positive(message = "Amount must be a positive number")
    @Digits(integer = 10, fraction = 2, message = "Amount must be a valid number with up to 2 decimal places")
    private double transactionValue;

    @NotNull(message = "TransactionDate cannot be null")
    private LocalDateTime transactionDate;

    @NotNull(message = "Transaction is expense cannot be null")
    private boolean expense;

    @ManyToOne
    @JoinColumn(name = "id", referencedColumnName = "id")
    private CategoryEntity category;

    @NotNull(message = "Transaction hashCode cannot be null")
    private String hashCode;

    @ManyToOne
    @JoinColumn(name = "username", referencedColumnName = "username")
    @NotNull(message = "Transaction user cannot be null")
    private UserEntity user;

    public TransactionEntity() {}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TransactionEntity that)) return false;
        return Objects.equals(transactionId, that.transactionId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(transactionId);
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return "Transaction{" +
                "id=" + transactionId +
                ", value=" + transactionValue +
                ", date=" + transactionDate.format(formatter) +
                ", isExpense=" + expense +
                ", category='" + category + '\'' +
                '}';
    }
}

