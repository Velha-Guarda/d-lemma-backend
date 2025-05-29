package com.velhaguarda.dlemma.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class DilemmaRequestDTO {
    @NotBlank
    private String title;

    @NotNull
    private UUID professorId;
}
