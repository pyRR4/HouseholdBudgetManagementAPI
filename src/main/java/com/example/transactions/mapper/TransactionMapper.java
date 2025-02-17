package com.example.transactions.mapper;

import com.example.transactions.dto.TransactionDTO;
import com.example.transactions.entity.Transaction;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TransactionMapper extends GenericMapper<Transaction, TransactionDTO> {
}
