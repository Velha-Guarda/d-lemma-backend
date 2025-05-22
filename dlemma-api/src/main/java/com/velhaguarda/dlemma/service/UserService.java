package com.velhaguarda.dlemma.service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.velhaguarda.dlemma.dto.UserPatchDTO;
import com.velhaguarda.dlemma.dto.UserResponseDTO;
import com.velhaguarda.dlemma.entity.User;
import com.velhaguarda.dlemma.enums.Role;
import com.velhaguarda.dlemma.mapper.UserMapper;
import com.velhaguarda.dlemma.repository.UserRepository;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService { // metodos para controlar usuario
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

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

    public UserResponseDTO patchTask(UUID id, @Valid UserPatchDTO dto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + id));

        User currentUser = getCurrentUser();
        if (!user.getId().equals(currentUser.getId())) {
            throw new RuntimeException("You do not have permission to update this user");
        }

        if (dto.getName() != null)
            user.setName(dto.getName());
        if (dto.getEmail() != null)
            user.setEmail(dto.getEmail());
        if (dto.getPassword() != null) {
            String encodedPassword = passwordEncoder.encode(dto.getPassword());
            user.setPassword(encodedPassword);
        }
        if (dto.getGraduation() != null)
            user.setGraduation(dto.getGraduation());
        if (dto.getRole() != null) {
            if (currentUser.getRole() != Role.DEV) {
                throw new RuntimeException("Only admins can change roles");
            }
            user.setRole(dto.getRole());
        }

        User updatedUser = userRepository.save(user);

        return userMapper.toResponseDTO(updatedUser);
    }
}
