package com.velhaguarda.dlemma.dto;

import com.velhaguarda.dlemma.enums.InvitationStatus;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InvitationResponseDTO {
    @NotNull
    private Integer chatId;

    @NotNull
    private InvitationStatus response;
}