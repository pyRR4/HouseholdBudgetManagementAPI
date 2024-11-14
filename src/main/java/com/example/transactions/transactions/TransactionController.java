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

    @GetMapping("/users/{username}/transactions")
    public ResponseEntity<List<TransactionResponse>> getTransactionsByUser(@PathVariable String username) {
        List<TransactionResponse> transactions = transactionService.getTransactionsByUser(username);

        return ResponseEntity
                .ok(transactions);
    }

    @GetMapping("/users/{username}/{category}/transactions")
    public ResponseEntity<List<TransactionResponse>> getTransactionsByUserAndCategory(
            @PathVariable String username,
            @PathVariable String category
    ) {
        List<TransactionResponse> transactions = transactionService.getTransactionsByUserAndCategory(username, category);

        return ResponseEntity
                .ok(transactions);
    }

    @PostMapping("/users/{username}/transactions") //TODO
    public ResponseEntity<TransactionResponse> createTransaction(@PathVariable String username, @RequestBody TransactionResponse transactionResponse) {
        TransactionResponse savedTransactionEntity = transactionService.createTransaction(username, transactionResponse);
        //zmienic na response i generowac kod do uri w response
        return ResponseEntity
                .created(linkTo(methodOn(TransactionController.class)
                        .getTransaction(username, savedTransactionEntity.getHashCode())).toUri())
                .body(transactionResponse);
    }

    @GetMapping("/users/{username}/transactions/{hashCode}")
    public ResponseEntity<TransactionResponse> getTransaction(@PathVariable String username, @PathVariable String hashCode) {
        TransactionResponse transactionResponse = transactionService.getTransaction(username, hashCode);

        return ResponseEntity
                .ok(transactionResponse);
    }

    @PutMapping("/users/{username}/transactions/{hashCode}")
    public ResponseEntity<TransactionResponse> updateTransaction(@PathVariable String username, @PathVariable String hashCode, @RequestBody TransactionResponse transactionResponse) {
        TransactionResponse updatedTransactionResponse = transactionService.updateTransaction(username, hashCode, transactionResponse);

        return ResponseEntity
                .ok(updatedTransactionResponse);
    }

    @DeleteMapping("/users/{username}/transactions/{hashCode}")
    public ResponseEntity<TransactionEntity> deleteTransaction(@PathVariable String username, @PathVariable String hashCode) {
        transactionService.deleteTransaction(username, hashCode);

        return ResponseEntity.noContent().build();
    }
}
