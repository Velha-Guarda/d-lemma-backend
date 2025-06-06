package com.velhaguarda.dlemma.dto;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDTO { // resposta em JSON
    private UUID id;
    private String name;
    private String email;
    private String graduation;
}
