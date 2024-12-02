package com.example.transactions.users;

import com.example.transactions.categories.CategoryEntity;
import com.example.transactions.transactions.TransactionEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Getter
@Setter
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"email", "username"}))
public class UserEntity {
    @Id
    @NotNull(message = "User name cannot be null")
    @Size(min = 8, message = "Username has to be at least 8 characters long")
    private String username;

    @NotNull(message = "User password cannot be null")
    @Size(min = 8, message = "Password has to be at least 8 characters long")
    private String password;

    @NotNull(message = "User nickname cannot be null")
    @Size(min = 5, max = 20, message = "Nickname has to be at least 5 and maximum 20 characters long")
    private String nickname;

    @NotNull(message = "User email cannot be null")
    @Email(message = "Invalid email format")
    private String email;

    @NotNull(message = "User balance cannot be null")
    @Digits(integer = 10, fraction = 2, message = "Amount must be a valid number with up to 2 decimal places")
    private BigDecimal balance;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<TransactionEntity> transactions;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<CategoryEntity> categories;

    public UserEntity() {}

    public UserEntity(String username, String password, String nickname, String email) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.email = email;
        this.balance = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
    }

}
