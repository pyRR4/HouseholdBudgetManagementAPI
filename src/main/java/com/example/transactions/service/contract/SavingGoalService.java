package com.example.transactions.service.contract;

import com.example.transactions.dto.SavingGoalDTO;
import com.example.transactions.service.generic.CreateDeleteService;
import com.example.transactions.service.generic.ReadOnlyService;

public interface SavingGoalService extends CreateDeleteService<SavingGoalDTO, Long>, ReadOnlyService<SavingGoalDTO, Long> {
    SavingGoalDTO updateCurrentAmount(Long id, double amountAdded);
}
