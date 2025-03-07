package com.example.transactions.services;

import com.example.transactions.dto.SavingGoalDTO;
import com.example.transactions.entity.SavingGoal;
import com.example.transactions.entity.User;
import com.example.transactions.exception.SavingGoalNotFound;
import com.example.transactions.mapper.SavingGoalMapper;
import com.example.transactions.mapper.UserMapper;
import com.example.transactions.repository.SavingGoalRepository;
import com.example.transactions.service.contract.implementation.SavingGoalServiceImpl;
import org.assertj.core.api.InstanceOfAssertFactories;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SavingGoalServiceTest {

    @Mock
    private SavingGoalRepository savingGoalRepository;

    @Mock
    private SavingGoalMapper savingGoalMapper;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private SavingGoalServiceImpl savingGoalService;

    private SavingGoal savingGoal;
    private SavingGoalDTO savingGoalDTO;

    @BeforeEach
    void setUp() {
        User user = new User(
                1L,
                "john_doe",
                "securePass123",
                "john@example.com",
                new BigDecimal("1000.00"),
                null,
                null);

        savingGoal = new SavingGoal(1L, "savingGoal", 0.0, 0.0, user);
        savingGoalDTO = new SavingGoalDTO(
                savingGoal.getId(),
                savingGoal.getTitle(),
                savingGoal.getAmount(),
                savingGoal.getCurrentAmount(),
                userMapper.toDTO(savingGoal.getUser())
        );
    }

    @Test
    void shouldUpdateCurrentAmount_WhenExists() {
        savingGoalDTO = new SavingGoalDTO(
                savingGoal.getId(),
                savingGoal.getTitle(),
                savingGoal.getAmount(),
                savingGoal.getCurrentAmount() + 50.0,
                userMapper.toDTO(savingGoal.getUser())
        );

        when(savingGoalMapper.toDTO(savingGoal)).thenReturn(savingGoalDTO);
        when(savingGoalRepository.findById(1L)).thenReturn(Optional.of(savingGoal));

        SavingGoalDTO dto = savingGoalService.updateCurrentAmount(1L, 50.0);

        assertThat(dto).isNotNull();
        assertThat(dto.currentAmount()).isEqualTo(50.0);
        verify(savingGoalRepository, times(1)).save(savingGoal);
    }

    @Test
    void shouldThrowException_WhenUpdatingNonExistingGoal() {
        when(savingGoalRepository.findById(savingGoal.getId())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> savingGoalService.updateCurrentAmount(savingGoal.getId(), 50.0))
                .isInstanceOf(SavingGoalNotFound.class)
                .hasMessageContaining(String.format("Saving goal with ID: %s not found.", savingGoal.getId()));
    }

    @Test
    void shouldCreateSavingGoal() {
        when(savingGoalMapper.toDTO(savingGoal)).thenReturn(savingGoalDTO);
        when(savingGoalRepository.save(savingGoal)).thenReturn(savingGoal);
        when(savingGoalMapper.toEntity(savingGoalDTO)).thenReturn(savingGoal);

        SavingGoalDTO created = savingGoalService.create(savingGoalDTO);

        assertThat(created).isNotNull();
        assertThat(created.id()).isEqualTo(savingGoal.getId());
        verify(savingGoalRepository, times(1)).save(savingGoal);
    }

    @Test
    void shouldDeleteSavingGoal_WhenExists() {
        when(savingGoalRepository.findById(savingGoal.getId())).thenReturn(Optional.of(savingGoal));

        savingGoalService.delete(savingGoal.getId());

        verify(savingGoalRepository, times(1)).delete(savingGoal);
        verify(savingGoalRepository, times(1)).findById(savingGoal.getId());
    }

    @Test
    void shouldThrowException_WhenDeletingNonExistingGoal() {
        when(savingGoalRepository.findById(savingGoal.getId())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> savingGoalService.delete(savingGoal.getId()))
                .isInstanceOf(SavingGoalNotFound.class)
                .hasMessageContaining(String.format("Saving goal with ID: %s not found.", savingGoal.getId()));
    }

    @Test
    void shouldGetSavingGoalById_WhenExists() {
        when(savingGoalRepository.findById(savingGoal.getId())).thenReturn(Optional.of(savingGoal));
        when(savingGoalMapper.toDTO(savingGoal)).thenReturn(savingGoalDTO);

        SavingGoalDTO saved = savingGoalService.getById(savingGoal.getId());

        assertThat(saved).isNotNull();
        assertThat(saved.id()).isEqualTo(savingGoal.getId());
    }

    @Test
    void shouldThrowException_WhenGettingNonExistingGoal() {
        when(savingGoalRepository.findById(savingGoal.getId())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> savingGoalService.getById(savingGoal.getId()))
                .isInstanceOf(SavingGoalNotFound.class)
                .hasMessageContaining(String.format("Saving goal with ID: %s not found.", savingGoal.getId()));
    }

    @Test
    void shouldGetAllSavingGoals() {
        List<SavingGoal> savingGoals = List.of(savingGoal);
        List<SavingGoalDTO> savingGoalDTOs = List.of(savingGoalDTO);

        when(savingGoalRepository.findAll()).thenReturn(savingGoals);
        when(savingGoalMapper.toDTO(savingGoal)).thenReturn(savingGoalDTO);

        List<SavingGoalDTO> found = savingGoalService.getAll();

        assertThat(found).asInstanceOf(InstanceOfAssertFactories.LIST).hasSize(1);
        assertThat(found.get(0).id()).isEqualTo(savingGoalDTO.id());
    }
}
