package com.velhaguarda.dlemma.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DilemmaStatusResponseDTO {
    private int idDilemma;
    private String title;
    private UUID professorId;
    private String invitationStatus;
    private Boolean isClosed;
    private LocalDateTime closedAt;
}
