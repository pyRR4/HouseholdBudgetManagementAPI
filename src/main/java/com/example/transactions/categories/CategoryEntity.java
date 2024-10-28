package com.example.transactions.categories;

import com.example.transactions.transactions.TransactionEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
public class CategoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @OneToMany(mappedBy = "transactionCategory")
    private List<TransactionEntity> transactions;

    public CategoryEntity() {}

    public CategoryEntity(String name) {
        this.name = name;
    }
}
