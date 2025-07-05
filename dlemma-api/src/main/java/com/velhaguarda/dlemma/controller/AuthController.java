package com.velhaguarda.dlemma.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.velhaguarda.dlemma.dto.AuthRequestDTO;

import com.velhaguarda.dlemma.dto.AuthResponseDTO;
import com.velhaguarda.dlemma.dto.EmailRequestDTO;
import com.velhaguarda.dlemma.dto.ResetPasswordDTO;
import com.velhaguarda.dlemma.dto.UserRequestDTO;
import com.velhaguarda.dlemma.service.AuthService;
import com.velhaguarda.dlemma.service.PasswordResetService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController { // tudo de login e registro aqui
    private final AuthService authService;
    private final PasswordResetService passwordResetService;

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
    public ResponseEntity<?> requestPasswordReset(@RequestBody EmailRequestDTO request) {
        passwordResetService.sendResetToken(request.getEmail());
        return ResponseEntity.ok("Token enviado com sucesso.");
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody ResetPasswordDTO request) {
        passwordResetService.resetPassword(request.getToken(), request.getNewPassword());
        return ResponseEntity.ok("Senha redefinida com sucesso.");
    }

}
