package com.example.transactions.transactions;

import com.example.transactions.categories.CategoryEntity;
import com.example.transactions.users.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TransactionRepository extends JpaRepository<TransactionEntity, Long> {
    Optional<TransactionEntity> findByHashCode(String hashCode);
    Optional<TransactionEntity> findAllByUser(UserEntity user);
    Optional<TransactionEntity> findAllByCategoryAndUser(CategoryEntity category, UserEntity user);

    boolean existsByHashCode(String hashCode);

    void deleteByHashCode(String hashCode);

}
