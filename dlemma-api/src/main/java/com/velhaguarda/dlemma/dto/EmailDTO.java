package com.velhaguarda.dlemma.dto;

public class EmailDTO {
    @jakarta.validation.constraints.Email
    @jakarta.validation.constraints.NotBlank
    private String email;
    // getter e setter
}