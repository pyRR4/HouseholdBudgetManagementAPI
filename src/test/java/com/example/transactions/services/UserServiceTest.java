package com.example.transactions.services;

import com.example.transactions.dto.UserDTO;
import com.example.transactions.entity.User;
import com.example.transactions.mapper.UserMapper;
import com.example.transactions.repository.UserRepository;
import com.example.transactions.service.contract.implementation.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
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

}
