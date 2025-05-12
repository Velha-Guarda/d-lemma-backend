package com.velhaguarda.dlemma.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.velhaguarda.dlemma.dto.UserRequestDTO;
import com.velhaguarda.dlemma.dto.UserResponseDTO;
import com.velhaguarda.dlemma.entity.User;
import com.velhaguarda.dlemma.mapper.UserMapper;
import com.velhaguarda.dlemma.repository.UserRepository;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    public UserResponseDTO createUser(@Valid UserRequestDTO dto) {
        if (userRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new RuntimeException("Email já cadastrado.");
        }
        logger.info("Creating user with email: {}", dto.getEmail());
        User user = userMapper.toEntity(dto);
        logger.info("User created: {}", user);

        User savedUser = userRepository.save(user);
        logger.info("User saved with ID: {}", savedUser.getId());

        return userMapper.toResponseDTO(savedUser);
    }

    public List<UserResponseDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(userMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public User getCurrentUser() { // get the current user who logged in
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}
