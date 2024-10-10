package com.example.transactions;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TransactionController {

    private final TransactionRepository transactionRepository;

    public TransactionController(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @GetMapping("/transactions")
    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    @PostMapping("/transactions")
    public Transaction createTransaction(@RequestBody Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    @GetMapping("/transactions/{id}")
    public Transaction getTransaction(@PathVariable Long id) {
        return transactionRepository.findById(id)
                .orElseThrow(() -> new TransactionNotFoundException(id));
    }

    @PutMapping("/transactions/{id}")
    public Transaction updateTransaction(@PathVariable Long id, @RequestBody Transaction transaction) {
        return transactionRepository.findById(id)
            .map(oldTransaction -> {
                oldTransaction.setTransaction_id(transaction.getTransaction_id());
                oldTransaction.setTransaction_date(transaction.getTransaction_date());
                oldTransaction.setTransaction_category(transaction.getTransaction_category());
                oldTransaction.setTransaction_value(transaction.getTransaction_value());
                return transactionRepository.save(oldTransaction);
            }).orElseGet(() -> transactionRepository.save(transaction));
    }

    @DeleteMapping("/transactions/{id}")
    public void deleteTransaction(@PathVariable Long id) {
        transactionRepository.deleteById(id);
    }
}
