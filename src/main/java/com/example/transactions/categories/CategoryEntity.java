package com.example.transactions.categories;

import com.example.transactions.transactions.TransactionEntity;
import com.example.transactions.users.UserEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"username", "name"}))
public class CategoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "category")
    @JsonIgnore
    private List<TransactionEntity> transactions;

    @ManyToOne()
    @JoinColumn(name = "username", referencedColumnName = "username")
    @JsonIgnore
    private UserEntity user;

    public CategoryEntity() {}
}
