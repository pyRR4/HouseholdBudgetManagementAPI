package com.example.transactions.transactions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        TransactionResponse savedTransactionEntity = transactionService.createTransaction(transactionResponse);
        //zmienic na response i generowac kod do uri w response
        return ResponseEntity
                .created(linkTo(methodOn(TransactionController.class)
                        .getTransaction(savedTransactionEntity.getHashCode())).toUri())
                .body(transactionResponse);
    }

    @GetMapping("/transactions/{hashCode}")
    public ResponseEntity<TransactionResponse> getTransaction(@PathVariable String hashCode) {
        TransactionResponse transactionResponse = transactionService.getTransaction(hashCode);

        return ResponseEntity
                .ok(transactionResponse);
    }

    @PutMapping("/transactions/{hashCode}")
    public ResponseEntity<TransactionResponse> updateTransaction(@PathVariable String hashCode, @RequestBody TransactionResponse transactionResponse) {
        TransactionResponse updatedTransactionResponse = transactionService.updateTransaction(hashCode, transactionResponse);

        return ResponseEntity
                .ok(updatedTransactionResponse);
    }

    @DeleteMapping("/transactions/{hashCode}")
    public ResponseEntity<TransactionEntity> deleteTransaction(@PathVariable String hashCode) {
        transactionService.deleteTransaction(hashCode);

        return ResponseEntity.noContent().build();
    }
}
