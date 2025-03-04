package com.example.transactions.service.contract;
import com.example.transactions.dto.TransactionDTO;
import com.example.transactions.service.generic.CreateDeleteService;
import com.example.transactions.service.generic.ReadOnlyService;

public interface TransactionService extends
        ReadOnlyService<TransactionDTO, Long>,
        CreateDeleteService<TransactionDTO, Long> {
}
