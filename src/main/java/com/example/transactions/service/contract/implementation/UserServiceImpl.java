package com.example.transactions.service.contract.implementation;

import com.example.transactions.dto.UserDTO;
import com.example.transactions.entity.User;
import com.example.transactions.exceptions.UserNotFound;
import com.example.transactions.mapper.UserMapper;
import com.example.transactions.repository.UserRepository;
import com.example.transactions.service.contract.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    private final UserRepository userRepository;

    @Override
    public UserDTO create(UserDTO dto) {
        User user = userMapper.toEntity(dto);

        log.debug("Created user: {}", user);

        userRepository.save(user);

        return userMapper.toDTO(user);
    }

    @Override
    public UserDTO update(Long id, UserDTO dto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFound(id));

        user.setBalance(user.getBalance());
        user.setEmail(user.getEmail());
        user.setPassword(user.getPassword());
        user.setUsername(user.getUsername());
        user.setTransactions(user.getTransactions());
        user.setCategories(user.getCategories());

        userRepository.save(user);

        log.debug("Updated user: {}", user);

        return userMapper.toDTO(user);
    }

    @Override
    public void delete(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFound(id));

        userRepository.delete(user);

        log.debug("Deleted user: {}", user);
    }

    @Override
    public UserDTO getById(Long id) {
        return userMapper.toDTO(userRepository.findById(id)
                .orElseThrow(() -> new UserNotFound(id)));
    }

    @Override
    public List<UserDTO> getAll() {
        return userRepository.findAll().stream()
                .map(userMapper::toDTO)
                .toList();
    }
}
