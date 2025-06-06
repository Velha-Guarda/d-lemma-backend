package com.velhaguarda.dlemma.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class DilemmaResponseDTO {
    private int id;
    private String title;
    private UUID professorId;
    private LocalDateTime createdAt;
    private boolean isClosed;
    private LocalDateTime closedAt;
}