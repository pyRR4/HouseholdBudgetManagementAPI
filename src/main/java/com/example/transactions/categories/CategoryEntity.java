package com.example.transactions.categories;

import com.example.transactions.transactions.TransactionEntity;
import com.example.transactions.users.UserEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"username", "name"}))
public class CategoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Category name cannot be null")
    @Size(min = 3, max = 20, message = "Category name has to be at least 3 and maximum 20 characters long")
    private String name;

    @OneToMany(mappedBy = "category")
    @JsonIgnore
    private List<TransactionEntity> transactions;

    @ManyToOne()
    @JoinColumn(name = "username", referencedColumnName = "username")
    @JsonIgnore
    @NotNull(message = "Category has to be assigned to the user")
    private UserEntity user;

    public CategoryEntity() {}

    @Override
    public String toString() {
        return "CategoryEntity{" +
                ", name='" + name + '\'' +
                ", user=" + user.getUsername() +
                '}';
    }
}
