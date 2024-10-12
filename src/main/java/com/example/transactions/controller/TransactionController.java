package com.example.transactions.controller;

import com.example.transactions.service.TransactionEntity;
import com.example.transactions.exceptions.TransactionNotFoundException;
import com.example.transactions.repository.TransactionRepository;
import com.example.transactions.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
public class TransactionController {

    private final TransactionRepository transactionRepository;
    private final TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionRepository transactionRepository, TransactionService transactionService) {
        this.transactionRepository = transactionRepository;
        this.transactionService = transactionService;
    }

    @GetMapping("/transactions")
    public ResponseEntity<List<TransactionEntity>> getAllTransactions() {
        List<TransactionEntity> transactions = transactionRepository.findAll();

        return ResponseEntity
                .ok(transactions);
    }

    @PostMapping("/transactions")
    public ResponseEntity<TransactionEntity> createTransaction(@RequestBody TransactionEntity transactionEntity) {
        TransactionEntity savedTransactionEntity = transactionRepository.save(transactionEntity);

        return ResponseEntity
                .created(linkTo(methodOn(TransactionController.class)
                        .getTransaction(savedTransactionEntity.getTransaction_id())).toUri())
                .body(savedTransactionEntity);
    }

    @GetMapping("/transactions/{id}")
    public ResponseEntity<TransactionEntity> getTransaction(@PathVariable Long id) {
        TransactionEntity transactionEntity = transactionRepository.findById(id)
                .orElseThrow(() -> new TransactionNotFoundException(id));

        return ResponseEntity
                .ok(transactionEntity);
    }

    @PutMapping("/transactions/{id}")
    public ResponseEntity<TransactionEntity> updateTransaction(@PathVariable Long id, @RequestBody TransactionEntity transactionEntity) {
        TransactionEntity updatedTransactionEntity = transactionService.updateTransaction(id, transactionEntity);

        return ResponseEntity
                .ok(updatedTransactionEntity);
    }

    @DeleteMapping("/transactions/{id}")
    public ResponseEntity<TransactionEntity> deleteTransaction(@PathVariable Long id) {
        transactionRepository.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
