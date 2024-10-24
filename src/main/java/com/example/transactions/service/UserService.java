package com.example.transactions.service;

import com.example.transactions.exceptions.UserNotFoundException;
import com.example.transactions.repository.UserRepository;
import org.hibernate.service.UnknownServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserEntity createUser(UserEntity user) {
        return userRepository.save(user);
    }

    public UserEntity updateUser(String username, UserEntity user) {
        return userRepository.findByUsername(username)
                .map(oldUser -> {
                    oldUser.setUsername(user.getUsername());
                    oldUser.setPassword(user.getPassword());
                    oldUser.setEmail(user.getEmail());
                    oldUser.setNickname(user.getNickname());
                    oldUser.setBalance(user.getBalance());
                    return userRepository.save(oldUser);
                }).orElseThrow(() -> new UserNotFoundException(username));
    }

    public UserEntity getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username));
    }

    public void deleteUser(String username) {
        userRepository.deleteByUsername(username);
    }
}
