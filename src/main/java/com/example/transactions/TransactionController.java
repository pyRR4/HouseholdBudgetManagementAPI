package com.example.transactions;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.parser.Entity;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
public class TransactionController {

    private final TransactionRepository transactionRepository;
    private final TransactionAssembler transactionAssembler;

    public TransactionController(TransactionRepository transactionRepository, TransactionAssembler transactionAssembler) {
        this.transactionRepository = transactionRepository;
        this.transactionAssembler = transactionAssembler;
    }

    @GetMapping("/transactions")
    public CollectionModel<EntityModel<Transaction>> getAllTransactions() {
        List<EntityModel<Transaction>> transactions = transactionRepository.findAll().stream()
                .map(transactionAssembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(transactions, linkTo(methodOn(TransactionController.class).getAllTransactions()).withSelfRel());
    }

    @PostMapping("/transactions")
    public EntityModel<Transaction> createTransaction(@RequestBody Transaction transaction) {
        return transactionAssembler.toModel(transactionRepository.save(transaction));
    }

    @GetMapping("/transactions/{id}")
    public EntityModel<Transaction> getTransaction(@PathVariable Long id) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new TransactionNotFoundException(id));

        return transactionAssembler.toModel(transaction);
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
