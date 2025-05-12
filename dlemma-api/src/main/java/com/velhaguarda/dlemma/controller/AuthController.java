package com.velhaguarda.dlemma.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.velhaguarda.dlemma.dto.UserRequestDTO;
import com.velhaguarda.dlemma.dto.UserResponseDTO;
import com.velhaguarda.dlemma.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController { //tudo de login e registro aqui
    private final UserService userService;    

    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> createUser(@RequestBody @Valid UserRequestDTO requestDTO) {
        UserResponseDTO responseDTO = userService.createUser(requestDTO);

        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }
}
