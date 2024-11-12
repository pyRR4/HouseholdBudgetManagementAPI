package com.example.transactions.categories;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {
    Optional<CategoryEntity> findByUserUsernameAndName(String username, String name);
    List<CategoryEntity> findAllByUserUsername(String username);

    boolean existsByUserUsernameAndName(String username, String name);

    void deleteByUserUsernameAndName(String username, String name);
}
