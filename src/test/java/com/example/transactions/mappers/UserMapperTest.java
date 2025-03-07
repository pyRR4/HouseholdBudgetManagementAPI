package com.example.transactions.mappers;

import com.example.transactions.dto.UserDTO;
import com.example.transactions.entity.User;
import com.example.transactions.mapper.UserMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class UserMapperTest {

    private final UserMapper userMapper = Mappers.getMapper(UserMapper.class);
    private User user;
    private UserDTO userDTO;

    @BeforeEach
    void setUp() {
        user = new User(1L, "john_doe", "securePass123", "john@example.com", new BigDecimal("1000.00"), null, null);
        userDTO = new UserDTO(user.getId(), user.getUsername(), user.getPassword(), user.getEmail(), user.getBalance().doubleValue());
    }

    @Test
    void shouldMapUserToDTO() {
        UserDTO dto = userMapper.toDTO(user);

        assertThat(dto).isNotNull();
        assertThat(dto.id()).isEqualTo(user.getId());
        assertThat(dto.username()).isEqualTo(user.getUsername());
        assertThat(dto.password()).isEqualTo(user.getPassword());
        assertThat(dto.email()).isEqualTo(user.getEmail());
        assertThat(dto.balance()).isEqualTo(user.getBalance().doubleValue());
    }

    @Test
    void shouldMapUserDTOToEntity() {
        User entity = userMapper.toEntity(userDTO);

        assertThat(entity).isNotNull();
        assertThat(entity.getId()).isEqualTo(userDTO.id());
        assertThat(entity.getUsername()).isEqualTo(userDTO.username());
        assertThat(entity.getPassword()).isEqualTo(userDTO.password());
        assertThat(entity.getEmail()).isEqualTo(userDTO.email());
        assertThat(entity.getBalance().doubleValue()).isEqualTo(userDTO.balance());
    }
}
