package com.velhaguarda.dlemma.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.velhaguarda.dlemma.dto.UserResponseDTO;
import com.velhaguarda.dlemma.entity.User;
import com.velhaguarda.dlemma.mapper.UserMapper;
import com.velhaguarda.dlemma.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService { // metodos para controlar usuario
    private final UserRepository userRepository;
    private final UserMapper userMapper;

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
