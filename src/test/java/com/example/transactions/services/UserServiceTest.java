package com.example.transactions.services;

import com.example.transactions.dto.UserDTO;
import com.example.transactions.entity.User;
import com.example.transactions.exception.UserNotFound;
import com.example.transactions.mapper.UserMapper;
import com.example.transactions.repository.UserRepository;
import com.example.transactions.service.contract.implementation.UserServiceImpl;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserServiceImpl userService;

    private User user;
    private UserDTO userDTO;

    @BeforeEach
    void setUp() {
        user = new User(1L, "john_doe", "securePass123", "john@example.com", new BigDecimal("1000.00"), null, null);
        userDTO = new UserDTO(user.getId(), user.getUsername(), user.getPassword(), user.getEmail(), user.getBalance().doubleValue());
    }

    @Test
    void shouldCreateUser() {
        when(userMapper.toEntity(any(UserDTO.class))).thenReturn(user);
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(userMapper.toDTO(any(User.class))).thenReturn(userDTO);

        UserDTO created = userService.create(userDTO);

        assertThat(created).isNotNull();
        assertThat(created.id()).isEqualTo(user.getId());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void shouldUpdateUser_WhenExists() {
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(userMapper.toDTO(any(User.class))).thenReturn(userDTO);

        UserDTO updated = userService.update(user.getId(), userDTO);

        assertThat(updated).isNotNull();
        assertThat(updated.id()).isEqualTo(userDTO.id());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void shouldThrowException_WhenUpdatingNonExistingUser() {
        when(userRepository.findById(user.getId())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userService.update(user.getId(), userDTO))
                .isInstanceOf(UserNotFound.class)
                .hasMessageContaining(String.format("User with ID: %s not found.", user.getId()));
    }

    @Test
    void shouldDeleteUser_WhenExists() {
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        userService.delete(user.getId());

        verify(userRepository, times(1)).delete(user);
    }

    @Test
    void shouldThrowException_WhenDeletingNonExistingUser() {
        when(userRepository.findById(user.getId())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userService.delete(user.getId()))
                .isInstanceOf(UserNotFound.class)
                .hasMessageContaining(String.format("User with ID: %s not found.", user.getId()));
    }

    @Test
    void shouldGetUserById_WhenExists() {
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(userMapper.toDTO(user)).thenReturn(userDTO);

        UserDTO found = userService.getById(user.getId());

        assertThat(found).isNotNull();
        assertThat(found.id()).isEqualTo(userDTO.id());
    }

    @Test
    void shouldThrowException_WhenGettingNonExistingUser() {
        when(userRepository.findById(user.getId())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userService.getById(user.getId()))
                .isInstanceOf(UserNotFound.class)
                .hasMessageContaining(String.format("User with ID: %s not found.", user.getId()));
    }

    @Test
    void shouldGetAllUsers() {
        List<User> users = List.of(user);
        List<UserDTO> userDTOs = List.of(userDTO);

        when(userRepository.findAll()).thenReturn(users);
        when(userMapper.toDTO(user)).thenReturn(userDTO);

        List<UserDTO> found = userService.getAll();

        assertThat(found).asInstanceOf(InstanceOfAssertFactories.LIST).hasSize(1);
        assertThat(found.get(0).id()).isEqualTo(userDTO.id());
    }

}
