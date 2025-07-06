package com.velhaguarda.dlemma.service;

import com.velhaguarda.dlemma.entity.User;
import com.velhaguarda.dlemma.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class PasswordResetService {

    private final UserRepository userRepository;
    private final MailService mailService;
    private final PasswordEncoder passwordEncoder;

    // Armazena tokens em memória (token -> email, expiração)
    private final Map<String, TokenInfo> tokens = new HashMap<>();

    private static class TokenInfo {
        String email;
        LocalDateTime expiresAt;

        TokenInfo(String email, LocalDateTime expiresAt) {
            this.email = email;
            this.expiresAt = expiresAt;
        }
    }

    public void sendResetToken(String email) {
        Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isEmpty()) {
            throw new RuntimeException("Usuário com este email não encontrado.");
        }

        String token = UUID.randomUUID().toString();
        tokens.put(token, new TokenInfo(email, LocalDateTime.now().plusMinutes(15)));

        String resetLink = "https://dlemma.netlify.app/resetar-senha?token=" + token;

        String message = """
                Olá!

                Recebemos uma solicitação para redefinir sua senha no d-Lemma.

                Use o link abaixo para redefinir sua senha (válido por 15 minutos):

                %s

                Caso não tenha solicitado, ignore este email.

                Equipe d-Lemma.
                """.formatted(resetLink);

        mailService.send(email, "Redefinição de senha - d-Lemma", message);
    }

    public void resetPassword(String token, String newPassword) {
        TokenInfo tokenInfo = tokens.get(token);

        if (tokenInfo == null || tokenInfo.expiresAt.isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Token inválido ou expirado.");
        }

        User user = userRepository.findByEmail(tokenInfo.email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado."));

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);

        tokens.remove(token); // Remove token após uso
    }
}
