package com.example.transactions.controller;

import com.example.transactions.service.TransactionEntity;
import com.example.transactions.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.transactions.controller.TransactionMapper.toEntity;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
public class TransactionController {

    private final TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping("/transactions")
    public ResponseEntity<List<TransactionResponse>> getAllTransactions() {
        List<TransactionResponse> transactions = transactionService.getAllTransactions();

        return ResponseEntity
                .ok(transactions);
    }

    @PostMapping("/transactions")
    public ResponseEntity<TransactionResponse> createTransaction(@RequestBody TransactionResponse transactionResponse) {
        TransactionEntity savedTransactionEntity = transactionService.createTransaction(toEntity(transactionResponse));
        //zmienic na response i generowac kod do uri w response
        return ResponseEntity
                .created(linkTo(methodOn(TransactionController.class)
                        .getTransaction(savedTransactionEntity.getTransaction_id())).toUri())
                .body(transactionResponse);
    }

    @GetMapping("/transactions/{id}")
    public ResponseEntity<TransactionResponse> getTransaction(@PathVariable Long id) {
        TransactionResponse transactionResponse = transactionService.getTransaction(id);

        return ResponseEntity
                .ok(transactionResponse);
    }

    @PutMapping("/transactions/{id}")
    public ResponseEntity<TransactionResponse> updateTransaction(@PathVariable Long id, @RequestBody TransactionEntity transactionEntity) {
        TransactionResponse updatedTransactionResponse = transactionService.updateTransaction(id, transactionEntity);

        return ResponseEntity
                .ok(updatedTransactionResponse);
    }

    @DeleteMapping("/transactions/{id}")
    public ResponseEntity<TransactionEntity> deleteTransaction(@PathVariable Long id) {
        transactionService.deleteTransaction(id);

        return ResponseEntity.noContent().build();
    }
}
