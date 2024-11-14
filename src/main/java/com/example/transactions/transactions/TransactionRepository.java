package com.example.transactions.transactions;

import com.example.transactions.categories.CategoryEntity;
import com.example.transactions.users.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TransactionRepository extends JpaRepository<TransactionEntity, Long> {
    Optional<TransactionEntity> findByUserUsernameAndHashCode(String username, String hashCode);
    List<TransactionEntity> findAllByUserUsername(String username);
    List<TransactionEntity> findAllByCategoryNameAndUserUsername(String category, String username);

    boolean existsByUserUsernameAndHashCode(String username, String hashCode);

    void deleteByUserUsernameAndHashCode(String username, String hashCode);

}
