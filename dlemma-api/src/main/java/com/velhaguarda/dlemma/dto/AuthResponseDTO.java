package com.velhaguarda.dlemma.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AuthResponseDTO {
    private UserResponseDTO user;
    private String token;
}
