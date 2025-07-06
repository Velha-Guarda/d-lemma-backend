package com.velhaguarda.dlemma.dto;

import com.velhaguarda.dlemma.enums.InvitationStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class ParticipantDTO {
    private UUID userId;
    private String userName;
    private String email;  
    private InvitationStatus invitationStatus;
    private LocalDateTime joinedAt;
    private int score;
}
