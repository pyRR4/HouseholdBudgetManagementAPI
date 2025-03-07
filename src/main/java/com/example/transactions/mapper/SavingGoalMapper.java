package com.example.transactions.mapper;

import com.example.transactions.dto.SavingGoalDTO;
import com.example.transactions.entity.SavingGoal;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SavingGoalMapper {
    @Mapping(target = "user", source = "userDTO")
    SavingGoal toEntity(SavingGoalDTO dto);

    @Mapping(target = "userDTO", source = "user")
    SavingGoalDTO toDTO(SavingGoal savingGoal);
}
