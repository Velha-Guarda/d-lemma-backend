package com.velhaguarda.dlemma.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthRequestDTO { //Request do login
    @NotBlank
    private String email;

    @NotBlank
    private String password;
}