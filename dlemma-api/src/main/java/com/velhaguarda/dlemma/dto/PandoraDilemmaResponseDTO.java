package com.velhaguarda.dlemma.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class PandoraDilemmaResponseDTO {
    private Integer id;
    private String dilemmaTitle;
    private UUID professorId;
    private LocalDateTime createdAt;
}