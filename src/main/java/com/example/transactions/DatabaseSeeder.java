package com.example.transactions;


import com.example.transactions.categories.CategoryEntity;
import com.example.transactions.categories.CategoryService;
import com.example.transactions.exceptions.CategoryAlredyExistsException;
import com.example.transactions.transactions.TransactionResponse;
import com.example.transactions.transactions.TransactionService;
import com.example.transactions.users.UserEntity;
import com.example.transactions.users.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Configuration
public class DatabaseSeeder implements CommandLineRunner {
    private static final Logger log = LoggerFactory.getLogger(DatabaseSeeder.class);

    private final TransactionService transactionService;
    private final UserService userService;
    private final CategoryService categoryService;

    @Autowired
    public DatabaseSeeder(TransactionService transactionService, UserService userService, CategoryService categoryService) {
        this.transactionService = transactionService;
        this.userService = userService;
        this.categoryService = categoryService;
    }

    @Override
    public void run(String... args) throws Exception {
        UserEntity user = createUser();
        List<CategoryEntity> categories = seedCategoriesForUser(user.getUsername());
        seedTransactions(user, categories);
    }

    private UserEntity createUser() {
        UserEntity user = new UserEntity("igor_podgorniak", "12345678", "igopod", "igopood33@gmail.com");
        return userService.createUser(user);
    }

    private List<CategoryEntity> seedCategoriesForUser(String username) {
        log.info("Seeding categories...");

        List<CategoryEntity> seededCategories = new ArrayList<>();
        List<String> categoryNames = Arrays.asList("Food", "Transport", "Entertainment", "Other");

        for (String categoryName : categoryNames) {
            try {
                CategoryEntity category = categoryService.createCategory(categoryName, username);
                seededCategories.add(category); // Dodajemy tylko pomyślnie stworzone kategorie
            } catch (CategoryAlredyExistsException e) {
                log.warn("Category '{}' already exists for user '{}'", categoryName, username);
            }
        }

        if (!seededCategories.isEmpty()) {
            log.info("Successfully added categories for user '{}': {}", username,
                    seededCategories.stream()
                            .map(CategoryEntity::getName)
                            .collect(Collectors.joining(", ")));
        } else {
            log.info("No new categories were added for user '{}'", username);
        }

        return seededCategories;
    }

    private void seedTransactions(UserEntity user, List<CategoryEntity> categories) {
        log.info("Seeding transactions...");
        Random random = new Random();

        for (int i = 0; i < 5; i++) {
            CategoryEntity randomCategory = categories.get(random.nextInt(categories.size()));
            TransactionResponse transaction = new TransactionResponse(
                    random.nextDouble() * 3000,
                    LocalDateTime.now(),
                    random.nextBoolean(),
                    randomCategory,
                    user
            );

            log.info("Preloading: " + transactionService.createTransaction(transaction));
        }
    }
}
