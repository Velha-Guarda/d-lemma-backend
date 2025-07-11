package com.velhaguarda.dlemma.service;

import com.velhaguarda.dlemma.dto.InvitationResponseDTO;
import com.velhaguarda.dlemma.dto.InviteRequestDTO;
import com.velhaguarda.dlemma.entity.*;
import com.velhaguarda.dlemma.enums.InvitationStatus;
import com.velhaguarda.dlemma.repository.*;
import com.velhaguarda.dlemma.security.CustomUserDetails;

import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InvitationService {

    private final UserRepository userRepository;
    private final DilemmaRepository dilemmaRepository;
    private final UsersChatsRepository usersChatsRepository;

    public void inviteUserToChat(InviteRequestDTO dto) {
        User user = userRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        Dilemma dilemma = dilemmaRepository.findById(dto.getChatId())
                .orElseThrow(() -> new RuntimeException("Dilema não encontrado"));

        UsersChats usersChats = new UsersChats();
        usersChats.setUser(user);
        usersChats.setDilemma(dilemma);
        usersChats.setInvitation(InvitationStatus.PENDING);
        usersChatsRepository.save(usersChats);
    }

    public void respondToInvitation(InvitationResponseDTO dto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        UUID userId = userDetails.getUser().getId();

        UsersChatsId id = new UsersChatsId(userId, dto.getChatId());
        UsersChats usersChats = usersChatsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Convite não encontrado."));

        usersChats.setInvitation(dto.getResponse());
        usersChats.setJoinedAt(LocalDateTime.now());
        usersChatsRepository.save(usersChats);
    }

}
