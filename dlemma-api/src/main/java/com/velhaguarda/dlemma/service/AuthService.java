package com.velhaguarda.dlemma.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.velhaguarda.dlemma.dto.UserRequestDTO;
import com.velhaguarda.dlemma.dto.UserResponseDTO;
import com.velhaguarda.dlemma.entity.User;
import com.velhaguarda.dlemma.mapper.UserMapper;
import com.velhaguarda.dlemma.repository.UserRepository;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {
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
}
