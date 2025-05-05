package com.velhaguarda.dlemma.mapper;

import org.springframework.stereotype.Component;

import com.velhaguarda.dlemma.dto.UserResponseDTO;
import com.velhaguarda.dlemma.dto.UserRequestDTO;
import com.velhaguarda.dlemma.entity.User;

@Component
public class UserMapper {
    public UserResponseDTO toResponseDTO(User user) {
        UserResponseDTO responseDTO = new UserResponseDTO();
        responseDTO.setId(user.getId());
        responseDTO.setName(
                user.getName() != null ? user.getName() : "No name provided");
        responseDTO.setEmail(
                user.getEmail() != null ? user.getEmail() : "No email provided");
        responseDTO.setGraduation(
                user.getGraduation() != null ? user.getGraduation() : "No graduation provided");

        return responseDTO;
    }

    public User toEntity(UserRequestDTO requestDTO) {
        User user = new User();

        user.setName(requestDTO.getName());
        user.setEmail(requestDTO.getEmail());
        user.setPassword(requestDTO.getPassword());
        user.setGraduation(requestDTO.getGraduation());

        return user;
    }
}
