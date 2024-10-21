package com.example.transactions;


import com.example.transactions.controller.TransactionResponse;
import com.example.transactions.service.TransactionEntity;
import com.example.transactions.service.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.util.Date;

@Configuration
public class DatabaseSeeder implements CommandLineRunner {
    private static final Logger log = LoggerFactory.getLogger(DatabaseSeeder.class);

    private final TransactionService transactionService;

    @Autowired
    public DatabaseSeeder(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @Override
    public void run(String... args) throws Exception {
        seedCategories();
        seedTransactions();
    }

    private void seedCategories() {
        log.info("Seeding categories...");
        log.info(TransactionEntity.addCategory("travel"));
        log.info(TransactionEntity.addCategory("bills"));
        log.info(TransactionEntity.addCategory("food"));
        log.info(TransactionEntity.addCategory("transport"));
    }

    private void seedTransactions() {
        log.info("Seeding transactions...");

        log.info("Preloading: " + transactionService.createTransaction(new TransactionResponse(2300.50, new Date(), true, "food")));
        log.info("Preloading: " + transactionService.createTransaction(new TransactionResponse(432.2, new Date(), true, "bills")));
        log.info("Preloading: " + transactionService.createTransaction(new TransactionResponse(3414.34, new Date(), false, "payment")));
        log.info("Preloading: " + transactionService.createTransaction(new TransactionResponse(897.43, new Date(), true, "food")));
        log.info("Preloading: " + transactionService.createTransaction(new TransactionResponse(2300.50, new Date(), false, "payment")));
    }
}
