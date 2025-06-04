package com.velhaguarda.dlemma.dto;

import com.velhaguarda.dlemma.enums.InvitationStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InvitationResponseDTO {
    @NotNull
    private Integer chatId;

    @NotNull
    private InvitationStatus response;
}