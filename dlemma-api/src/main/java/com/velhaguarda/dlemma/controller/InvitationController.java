package com.velhaguarda.dlemma.controller;

import com.velhaguarda.dlemma.dto.InvitationResponseDTO;
import com.velhaguarda.dlemma.dto.InviteRequestDTO;
import com.velhaguarda.dlemma.service.InvitationService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/invitations")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class InvitationController {

    private final InvitationService invitationService;

    @Operation(summary = "Convite para o dilemma", 
    description = "Convida um usuário para participar de um dilema. " +
                  "O usuário convidado deve aceitar o convite para participar do chat.")
    @PostMapping("/invite")
    public ResponseEntity<String> inviteUser(@RequestBody @Valid InviteRequestDTO dto) {
        invitationService.inviteUserToChat(dto);
        return ResponseEntity.ok("Convite enviado com sucesso.");
    }

    @Operation(summary = "Responder convite", 
    description = "Registra a resposta do usuário ao convite para participar de um dilema. " +
                  "O usuário deve aceitar ou recusar o convite.")
    @PostMapping("/respond")
    public ResponseEntity<String> respondToInvitation(@RequestBody @Valid InvitationResponseDTO dto) {
        invitationService.respondToInvitation(dto);
        return ResponseEntity.ok("Resposta registrada com sucesso.");
    }

}
