package com.example.transactions.mappers;

import com.example.transactions.dto.SavingGoalDTO;
import com.example.transactions.dto.UserDTO;
import com.example.transactions.entity.SavingGoal;
import com.example.transactions.entity.User;
import com.example.transactions.mapper.SavingGoalMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.*;

class SavingGoalMapperTest {

    private final SavingGoalMapper mapper = Mappers.getMapper(SavingGoalMapper.class);

    private SavingGoal savingGoal;
    private SavingGoalDTO savingGoalDTO;

    @BeforeEach
    void setUp() {
        User user = new User(1L, "john_doe", "securePass123", "john@example.com", null, null, null);
        UserDTO userDTO = new UserDTO(user.getId(), user.getUsername(), user.getPassword(), user.getEmail(), 1000.0);

        savingGoalDTO = new SavingGoalDTO(1L, "New Car", 10000.0, 500.0, userDTO);
        savingGoal = new SavingGoal(1L, "New Car", 10000.0, 500.0, user);
    }

    @Test
    void shouldMapSavingGoalToEntity() {
        SavingGoal entity = mapper.toEntity(savingGoalDTO);

        assertNotNull(entity);
        assertEquals(savingGoalDTO.id(), entity.getId());
        assertEquals(savingGoalDTO.title(), entity.getTitle());
        assertEquals(savingGoalDTO.amount(), entity.getAmount());
        assertEquals(savingGoalDTO.currentAmount(), entity.getCurrentAmount());
        assertNotNull(entity.getUser());
        assertEquals(savingGoalDTO.userDTO().id(), entity.getUser().getId());
    }

    @Test
    void shouldMapSavingGoalToDTO() {
        SavingGoalDTO dto = mapper.toDTO(savingGoal);

        assertNotNull(dto);
        assertEquals(savingGoal.getId(), dto.id());
        assertEquals(savingGoal.getTitle(), dto.title());
        assertEquals(savingGoal.getAmount(), dto.amount());
        assertEquals(savingGoal.getCurrentAmount(), dto.currentAmount());
        assertNotNull(dto.userDTO());
        assertEquals(savingGoal.getUser().getId(), dto.userDTO().id());
    }
}
