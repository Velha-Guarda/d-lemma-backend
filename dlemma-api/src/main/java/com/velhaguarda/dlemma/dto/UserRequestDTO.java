package com.velhaguarda.dlemma.dto;

import com.velhaguarda.dlemma.enums.Role;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestDTO { // request feito pelo usuario
    @NotBlank
    @Size(max = 100)
    private String name;

    @NotBlank
    @Size(max = 150)
    private String email;

    @NotBlank
    private String password;

    @NotBlank
    @Size(max = 100)
    private String graduation;

    @NotNull
    private Role role; // Enum: STUDENT ou PROFESSOR
}
