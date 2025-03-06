package com.example.transactions.service.contract.implementation;

import com.example.transactions.dto.UserDTO;
import com.example.transactions.entity.User;
import com.example.transactions.exception.UserNotFound;
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

        userRepository.save(user);

        log.info("Created user: {}", user);

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
        user.setCategories(user.getCategories());

        userRepository.save(user);

        log.info("Updated user: {}", user);

        return userMapper.toDTO(user);
    }

    @Override
    public void delete(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFound(id));

        userRepository.delete(user);

        log.info("Deleted user: {}", user);
    }

    @Override
    public UserDTO getById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFound(id));

        log.info("Retrieved user: {}", user);

        return userMapper.toDTO(user);
    }

    @Override
    public List<UserDTO> getAll() {
        List<User> users = userRepository.findAll();

        log.info("Retrieved all users: {}", users);

        return users.stream()
                .map(userMapper::toDTO)
                .toList();
    }
}
