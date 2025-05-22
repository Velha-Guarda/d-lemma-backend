package com.velhaguarda.dlemma.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.velhaguarda.dlemma.enums.Role;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserPatchDTO {
    private String name;
    private String email;
    private String password;
    private String graduation;
    private Role role;
}
