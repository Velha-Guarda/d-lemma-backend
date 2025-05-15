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
    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

    public UserResponseDTO createUser(@Valid UserRequestDTO dto) {
        if (userRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new RuntimeException("Email já cadastrado.");
        }

        logger.info("Creating user with email: {}", dto.getEmail());
        User user = userMapper.toEntity(dto);
        User savedUser = userRepository.save(user);
        logger.info("User saved with ID: {}", savedUser.getId());

        return userMapper.toResponseDTO(savedUser);
    }

    public LoginResponseDTO login(LoginRequestDTO dto) {
        //Verifica email ja existente
        User user = userRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new RuntimeException("Email não encontrado."));

        //compara com a senha criptografada automaticamente no registro (metodo toEntity faz isso)
        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new RuntimeException("Senha incorreta.");
        }

        //Devolutiva ao usuario
        LoginResponseDTO response = new LoginResponseDTO();
        response.setMessage("Login realizado com sucesso.");
        response.setUser(userMapper.toResponseDTO(user));
        return response;
    }
}
