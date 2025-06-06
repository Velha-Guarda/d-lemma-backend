package com.velhaguarda.dlemma.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.velhaguarda.dlemma.dto.UserResponseDTO;
import com.velhaguarda.dlemma.entity.User;
import com.velhaguarda.dlemma.mapper.UserMapper;
import com.velhaguarda.dlemma.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/users")
@SecurityRequirement(name = "bearerAuth")
@RequiredArgsConstructor
public class UserController { // tudo de usuario aqui
    private final UserService userService;
    private final UserMapper userMapper;

    @Operation(summary = "Listar usuários", 
    description = "Lista todos os usuários do sistema. " +
                  "Apenas usuários com a role 'PROFESSOR' ou 'DEV' podem acessar este endpoint.")
    @PreAuthorize("hasRole('PROFESSOR') or hasRole('DEV')")
    @GetMapping()
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        List<UserResponseDTO> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @Operation(summary = "Dados do atual usuário", 
    description = "Retorna os dados do usuário autenticado.")
    @GetMapping("/me")
    public ResponseEntity<UserResponseDTO> getCurrentUser() {
        User user = userService.getCurrentUser();
        return ResponseEntity.ok(userMapper.toResponseDTO(user));
    }
    
}
