package com.velhaguarda.dlemma.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InviteRequestDTO {
    @Email
    @NotBlank
    private String email;

    @NotNull
    private Integer chatId;
}
