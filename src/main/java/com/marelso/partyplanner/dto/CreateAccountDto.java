package com.marelso.partyplanner.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateAccountDto {
    private String username;
    private String name;
    private String lastName;
    private String email;
    private String password;
}
