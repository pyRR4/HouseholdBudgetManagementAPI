package com.example.transactions;

import lombok.NonNull;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class TransactionAssembler implements RepresentationModelAssembler<Transaction, EntityModel<Transaction>> {

    @Override
    public @NonNull EntityModel<Transaction> toModel(@NonNull Transaction transaction) {
        return EntityModel.of(transaction,
                linkTo(methodOn(TransactionController.class).getTransaction(transaction.getTransaction_id())).withSelfRel(),
                linkTo(methodOn(TransactionController.class).getAllTransactions()).withRel("transactions"));
    }
}
