package com.velhaguarda.dlemma.controller;

import com.velhaguarda.dlemma.dto.InvitationResponseDTO;
import com.velhaguarda.dlemma.dto.InviteRequestDTO;
import com.velhaguarda.dlemma.service.InvitationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/invitations")
@RequiredArgsConstructor
public class InvitationController {

    private final InvitationService invitationService;

    @PostMapping("/invite")
    public ResponseEntity<String> inviteUser(@RequestBody @Valid InviteRequestDTO dto) {
        invitationService.inviteUserToChat(dto);
        return ResponseEntity.ok("Convite enviado com sucesso.");
    }

    @PostMapping("/respond")
    public ResponseEntity<String> respondToInvitation(@RequestBody @Valid InvitationResponseDTO dto) {
        invitationService.respondToInvitation(dto);
        return ResponseEntity.ok("Resposta registrada com sucesso.");
    }

}
