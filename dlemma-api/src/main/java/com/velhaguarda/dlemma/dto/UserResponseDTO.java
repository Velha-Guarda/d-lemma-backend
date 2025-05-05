package com.velhaguarda.dlemma.dto;

import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponseDTO {
    private UUID id;
    private String name;
    private String email;
    private String graduation;
}
