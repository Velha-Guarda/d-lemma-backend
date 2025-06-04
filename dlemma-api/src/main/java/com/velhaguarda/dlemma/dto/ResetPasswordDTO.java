package com.velhaguarda.dlemma.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResetPasswordDTO {
    @jakarta.validation.constraints.NotBlank
    private String token;

    @jakarta.validation.constraints.NotBlank
    private String newPassword;
    // getter e setter
}
