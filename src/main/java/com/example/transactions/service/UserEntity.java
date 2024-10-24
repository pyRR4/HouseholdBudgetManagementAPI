package com.example.transactions.service;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
public class UserEntity {
    @Id
    private String username;
    private String password;
    private String nickname;
    private String email;
    private double balance;
    @OneToMany(mappedBy = "user")
    private List<TransactionEntity> transactions;

    public UserEntity() {}

    public UserEntity(String username, String password, String nickname, String email) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.email = email;
        this.balance = 0;
    }

}
