package com.velhaguarda.dlemma.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.velhaguarda.dlemma.dto.*;
import com.velhaguarda.dlemma.dto.AuthResponseDTO;
import com.velhaguarda.dlemma.dto.UserRequestDTO;
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
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

    // Registro de novo usuário
    public AuthResponseDTO createUser(@Valid UserRequestDTO dto) {
        if (userRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new RuntimeException("Email já cadastrado.");
        }

        logger.info("Creating user with email: {}", dto.getEmail());

        User user = userMapper.toEntity(dto); // já codifica a senha e define role
        User savedUser = userRepository.save(user);

        logger.info("User saved: {}", savedUser.getEmail());

        String token = jwtService.generateToken(savedUser);
        return new AuthResponseDTO(userMapper.toResponseDTO(savedUser), token);
    }

    // Login
    public AuthResponseDTO login(AuthRequestDTO dto) {
        User user = userRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new RuntimeException("Email não encontrado."));

        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new RuntimeException("Senha incorreta.");
        }

        String token = jwtService.generateToken(user);
        return new AuthResponseDTO(userMapper.toResponseDTO(user), token);
    }
}
