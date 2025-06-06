package com.velhaguarda.dlemma.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DilemmaStatusResponseDTO {
    private int idDilemma;
    private String title;
    private UUID professorId;
    private String invitationStatus;
    private Boolean isClosed;
    private LocalDateTime closedAt;
}
