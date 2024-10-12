package com.example.transactions;


import com.example.transactions.repository.TransactionRepository;
import com.example.transactions.service.TransactionEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Date;

@Configuration
public class LoadDatabase {
    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(TransactionRepository repository) {
        return args -> {
            log.info("Seeding categories...");
            TransactionEntity.addCategory("travel");
            TransactionEntity.addCategory("bills");
            TransactionEntity.addCategory("transport");
            TransactionEntity.addCategory("food");

            log.info("Loading data to database...");

            log.info("Preloading:" + repository.save(new TransactionEntity(2300.50, new Date(), true, "food")));
            log.info("Preloading:" + repository.save(new TransactionEntity(432.2, new Date(), true, "bills")));
            log.info("Preloading:" + repository.save(new TransactionEntity(3414.34, new Date(), false, "payment")));
            log.info("Preloading:" + repository.save(new TransactionEntity(897.43, new Date(), true, "food")));
            log.info("Preloading:" + repository.save(new TransactionEntity(2300.50, new Date(), false, "payment")));
        };
    }
}
