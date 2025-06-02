package com.velhaguarda.dlemma.dto;

public class ResetPasswordDTO {
    @jakarta.validation.constraints.NotBlank
    private String token;

    @jakarta.validation.constraints.NotBlank
    private String newPassword;
    // getter e setter
}
