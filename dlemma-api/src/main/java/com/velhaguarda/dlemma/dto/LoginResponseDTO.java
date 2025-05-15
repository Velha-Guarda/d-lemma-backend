package com.velhaguarda.dlemma.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponseDTO {
    private String message;
    private UserResponseDTO user;
}