package com.velhaguarda.dlemma.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.velhaguarda.dlemma.dto.AuthRequestDTO;

import com.velhaguarda.dlemma.dto.AuthResponseDTO;
import com.velhaguarda.dlemma.dto.EmailDTO;
import com.velhaguarda.dlemma.dto.ResetPasswordDTO;
import com.velhaguarda.dlemma.dto.UserRequestDTO;
import com.velhaguarda.dlemma.service.AuthService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController { // tudo de login e registro aqui
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponseDTO> createUser(@RequestBody @Valid UserRequestDTO requestDTO) {
        AuthResponseDTO responseDTO = authService.createUser(requestDTO);
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody @Valid AuthRequestDTO requestDTO) {
        AuthResponseDTO responseDTO = authService.login(requestDTO);
        return ResponseEntity.ok(responseDTO);
    }

    @PostMapping("/request-password-reset")
    public ResponseEntity<String> requestPasswordReset(@RequestBody EmailDTO dto) {
        authService.requestPasswordReset(dto.getEmail());
        return ResponseEntity.ok("Se o e-mail estiver cadastrado, você receberá instruções.");
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody ResetPasswordDTO dto) {
        authService.resetPassword(dto.getToken(), dto.getNewPassword());
        return ResponseEntity.ok("Senha redefinida com sucesso.");
    }
}
