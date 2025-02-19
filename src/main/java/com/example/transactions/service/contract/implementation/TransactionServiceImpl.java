package com.example.transactions.service.contract.implementation;

import com.example.transactions.dto.TransactionDTO;
import com.example.transactions.entity.Transaction;
import com.example.transactions.exceptions.TransactionNotFound;
import com.example.transactions.mapper.TransactionMapper;
import com.example.transactions.repository.TransactionRepository;
import com.example.transactions.service.contract.TransactionService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final TransactionMapper transactionMapper;

    @Override
    public TransactionDTO create(TransactionDTO transactionDTO) {
        Transaction transaction = transactionMapper.toEntity(transactionDTO);

        log.debug("Created transaction: {}", transaction);

        transactionRepository.save(transaction);

        return transactionMapper.toDTO(transaction);
    }

    @Override
    public void delete(Long id) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new TransactionNotFound(id));

        transactionRepository.delete(transaction);

        log.debug("Deleted transaction: {}", transaction);
    }

    @Override
    public TransactionDTO getById(Long id) {
        return transactionMapper.toDTO(transactionRepository.findById(id)
                .orElseThrow(() -> new TransactionNotFound(id)));
    }

    @Override
    public List<TransactionDTO> getAll() {
        return transactionRepository.findAll().stream()
                .map(transactionMapper::toDTO)
                .toList();
    }
}
