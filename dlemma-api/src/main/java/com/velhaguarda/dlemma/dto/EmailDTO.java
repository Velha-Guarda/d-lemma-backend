package com.velhaguarda.dlemma.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmailDTO {
    @jakarta.validation.constraints.Email
    @jakarta.validation.constraints.NotBlank
    private String email;
    // getter e setter
}