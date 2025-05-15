package com.velhaguarda.dlemma.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequestDTO { //Request do login
    @NotBlank
    private String email;

    @NotBlank
    private String password;
}