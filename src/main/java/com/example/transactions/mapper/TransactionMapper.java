package com.example.transactions.mapper;

import com.example.transactions.dto.TransactionDTO;
import com.example.transactions.entity.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TransactionMapper extends GenericMapper<Transaction, TransactionDTO> {

    @Mapping(target = "category", source = "categoryDTO")
    Transaction toEntity(TransactionDTO dto);

    @Mapping(target = "categoryDTO", source = "category")
    TransactionDTO toDTO(Transaction entity);
}
