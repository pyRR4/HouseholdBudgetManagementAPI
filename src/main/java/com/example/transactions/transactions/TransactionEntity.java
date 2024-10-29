package com.example.transactions.transactions;

import com.example.transactions.categories.CategoryEntity;
import com.example.transactions.users.UserEntity;
import jakarta.persistence.*;
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

    @Column(nullable = false)
    private double transactionValue;

    @Column(nullable = false)
    private LocalDateTime transactionDate;

    @Column(nullable = false)
    private boolean expense;

    @ManyToOne
    @JoinColumn(name = "id", referencedColumnName = "id")
    private CategoryEntity transactionCategory;

    @Column(nullable = false)
    private String hashCode;

    @ManyToOne
    @JoinColumn(name = "username", referencedColumnName = "username")
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
                ", category='" + transactionCategory + '\'' +
                '}';
    }
}

