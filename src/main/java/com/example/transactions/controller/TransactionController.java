package com.example.transactions.controller;

import com.example.transactions.dto.TransactionDTO;
import com.example.transactions.service.contract.TransactionService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("/transactions")
@AllArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class TransactionController {

    private TransactionService transactionService;

    @GetMapping
    public ResponseEntity<List<TransactionDTO>> getAllTransactions() {
        List<TransactionDTO> transactions = transactionService.getAll();

        log.info("Returning all transactions for logged user: {}", transactions.toString()); ///AUTHENTICATION SERVICE FOR RETRIEVING CURRENTLY LOGGED USER

        return ResponseEntity
                .ok(transactions);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransactionDTO> getTransactionById(@PathVariable Long id) {
        TransactionDTO transaction = transactionService.getById(id);

        log.info("Returning transaction: {}", transaction.toString());

        return ResponseEntity
                .ok(transaction);
    }

    @PostMapping
    public ResponseEntity<TransactionDTO> createTransaction(@RequestBody TransactionDTO transaction) {
        TransactionDTO createdTransaction = transactionService.create(transaction);

        log.info("Created transaction: {}", createdTransaction.toString());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createdTransaction);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTransaction(@PathVariable Long id) {
        transactionService.delete(id);

        log.info("Deleted transaction: {}", id);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

}
