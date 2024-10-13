package com.example.transactions.controller;

import com.example.transactions.service.TransactionEntity;
import com.example.transactions.exceptions.TransactionNotFoundException;
import com.example.transactions.repository.TransactionRepository;
import com.example.transactions.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.transactions.controller.TransactionMapper.toEntity;
import static com.example.transactions.controller.TransactionMapper.toResponse;
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
    public ResponseEntity<List<TransactionResponse>> getAllTransactions() {
        List<TransactionResponse> transactions = transactionRepository.findAll().stream()
                .map(TransactionMapper::toResponse)
                .toList();

        return ResponseEntity
                .ok(transactions);
    }

    @PostMapping("/transactions")
    public ResponseEntity<TransactionResponse> createTransaction(@RequestBody TransactionResponse transactionResponse) {
        TransactionEntity savedTransactionEntity = transactionRepository.save(toEntity(transactionResponse));

        return ResponseEntity
                .created(linkTo(methodOn(TransactionController.class)
                        .getTransaction(savedTransactionEntity.getTransaction_id())).toUri())
                .body(transactionResponse);
    }

    @GetMapping("/transactions/{id}")
    public ResponseEntity<TransactionResponse> getTransaction(@PathVariable Long id) {
        TransactionEntity transactionEntity = transactionRepository.findById(id)
                .orElseThrow(() -> new TransactionNotFoundException(id));

        TransactionResponse transactionResponse = toResponse(transactionEntity);

        return ResponseEntity
                .ok(transactionResponse);
    }

    @PutMapping("/transactions/{id}")
    public ResponseEntity<TransactionResponse> updateTransaction(@PathVariable Long id, @RequestBody TransactionEntity transactionEntity) {
        TransactionEntity updatedTransactionEntity = transactionService.updateTransaction(id, transactionEntity);

        TransactionResponse updatedTransactionResponse = toResponse(updatedTransactionEntity);

        return ResponseEntity
                .ok(updatedTransactionResponse);
    }

    @DeleteMapping("/transactions/{id}")
    public ResponseEntity<TransactionEntity> deleteTransaction(@PathVariable Long id) {
        transactionRepository.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
