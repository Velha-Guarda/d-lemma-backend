package com.velhaguarda.dlemma.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.velhaguarda.dlemma.dto.*;
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

    public void requestPasswordReset(String email) {
        userRepository.findByEmail(email).ifPresent(user -> {
            String token = jwtService.generateResetPasswordToken(email);
            String resetLink = "https://dlemma.netlify.app/reset-password?token=" + token;

            // Aqui você pode usar EmailService — por enquanto, faça só log
            logger.info("Link de reset para {}: {}", email, resetLink);
        });
    }

    public void resetPassword(String token, String newPassword) {
        if (!jwtService.isResetPasswordTokenValid(token)) {
            throw new RuntimeException("Token inválido ou expirado.");
        }

        String email = jwtService.extractEmail(token);
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado."));

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }
}
