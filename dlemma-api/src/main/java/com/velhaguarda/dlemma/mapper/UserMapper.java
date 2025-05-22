package com.velhaguarda.dlemma.mapper;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.velhaguarda.dlemma.dto.UserResponseDTO;
import com.velhaguarda.dlemma.dto.UserRequestDTO;
import com.velhaguarda.dlemma.entity.User;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserMapper { // converte de request para entidade e de entidade para response (JSON)
    private final PasswordEncoder passwordEncoder;

    public UserResponseDTO toResponseDTO(User user) {
        UserResponseDTO responseDTO = new UserResponseDTO();
        responseDTO.setId(user.getId());
        responseDTO.setName(
                user.getName() != null ? user.getName() : "No name provided");
        responseDTO.setEmail(
                user.getEmail() != null ? user.getEmail() : "No email provided");
        responseDTO.setGraduation(
                user.getGraduation() != null ? user.getGraduation() : "No graduation provided");
        responseDTO.setRole(
                user.getRole().name() != null ? user.getRole().name() : "No role provided");

        return responseDTO;
    }

    public User toEntity(UserRequestDTO requestDTO) {
        User user = new User();

        user.setName(requestDTO.getName());
        user.setEmail(requestDTO.getEmail());
        user.setPassword(passwordEncoder.encode(requestDTO.getPassword()));
        user.setGraduation(requestDTO.getGraduation());
        user.setRole(requestDTO.getRole());

        return user;
    }
}
