package com.example.transactions.repository;

import com.example.transactions.service.TransactionEntity;
import com.example.transactions.service.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TransactionRepository extends JpaRepository<TransactionEntity, Long> {
    Optional<TransactionEntity> findByHashCode(String hashCode);
    boolean existsByHashCode(String hashCode);
    void deleteByHashCode(String hashCode);

    Optional<TransactionEntity> findByUser(UserEntity user);
}
