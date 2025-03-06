package com.example.transactions.service.contract.implementation;

import com.example.transactions.dto.SavingGoalDTO;
import com.example.transactions.entity.SavingGoal;
import com.example.transactions.exception.SavingGoalNotFound;
import com.example.transactions.mapper.SavingGoalMapper;
import com.example.transactions.repository.SavingGoalRepository;
import com.example.transactions.service.contract.SavingGoalService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class SavingGoalServiceImpl implements SavingGoalService {

    private final SavingGoalRepository repository;
    private final SavingGoalMapper mapper;

    @Override
    public SavingGoalDTO updateCurrentAmount(Long id, double amountAdded) {
        SavingGoal savingGoal = repository.findById(id)
                .orElseThrow(() -> new SavingGoalNotFound(id));

        savingGoal.setAmount(savingGoal.getAmount() + amountAdded);

        repository.save(savingGoal);

        log.info("Saving goal amount updated to {}", savingGoal.getAmount());

        return mapper.toDTO(savingGoal);
    }

    @Override
    public SavingGoalDTO create(SavingGoalDTO dto) {
        SavingGoal savingGoal = mapper.toEntity(dto);

        savingGoal = repository.save(savingGoal);

        log.info("Created saving goal: {}", savingGoal);

        return mapper.toDTO(savingGoal);
    }

    @Override
    public void delete(Long id) {
        SavingGoal savingGoal = repository.findById(id)
                        .orElseThrow(() -> new SavingGoalNotFound(id));

        repository.delete(savingGoal);

        log.info("Deleted saving goal: {}", id);
    }

    @Override
    public SavingGoalDTO getById(Long id) {
        SavingGoal savingGoal = repository.findById(id)
                .orElseThrow(() -> new SavingGoalNotFound(id));

        log.info("Retrieved saving goal: {}", savingGoal);

        return mapper.toDTO(savingGoal);
    }

    @Override
    public List<SavingGoalDTO> getAll() {
        List<SavingGoal> savingGoals = repository.findAll();

        log.info("Retrieved saving goals for logged user: {}", savingGoals);

        return savingGoals.stream()
                .map(mapper::toDTO)
                .toList();
    }
}
