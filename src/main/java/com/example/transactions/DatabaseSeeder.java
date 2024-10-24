package com.example.transactions;


import com.example.transactions.controller.TransactionResponse;
import com.example.transactions.service.TransactionEntity;
import com.example.transactions.service.TransactionService;
import com.example.transactions.service.UserEntity;
import com.example.transactions.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;

@Configuration
public class DatabaseSeeder implements CommandLineRunner {
    private static final Logger log = LoggerFactory.getLogger(DatabaseSeeder.class);

    private final TransactionService transactionService;
    private final UserService userService;

    @Autowired
    public DatabaseSeeder(TransactionService transactionService, UserService userService) {
        this.transactionService = transactionService;
        this.userService = userService;
    }

    @Override
    public void run(String... args) throws Exception {
        seedCategories();
        seedTransactions(createUser());
    }

    private UserEntity createUser() {
        UserEntity user = new UserEntity("igor_podgorniak", "12345678", "igopod", "igopood33@gmail.com");
        return userService.createUser(user);
    }

    private void seedCategories() {
        log.info("Seeding categories...");
        log.info(TransactionEntity.addCategory("travel"));
        log.info(TransactionEntity.addCategory("bills"));
        log.info(TransactionEntity.addCategory("food"));
        log.info(TransactionEntity.addCategory("transport"));
    }

    private void seedTransactions(UserEntity user) {
        log.info("Seeding transactions...");

        log.info("Preloading: " + transactionService.createTransaction(new TransactionResponse(2300.50, LocalDateTime.now(), true, "food", user)));
        log.info("Preloading: " + transactionService.createTransaction(new TransactionResponse(432.2, LocalDateTime.now(), true, "bills", user)));
        log.info("Preloading: " + transactionService.createTransaction(new TransactionResponse(3414.34, LocalDateTime.now(), false, "payment", user)));
        log.info("Preloading: " + transactionService.createTransaction(new TransactionResponse(897.43, LocalDateTime.now(), true, "food", user)));
        log.info("Preloading: " + transactionService.createTransaction(new TransactionResponse(2300.50, LocalDateTime.now(), false, "payment", user)));
    }
}
