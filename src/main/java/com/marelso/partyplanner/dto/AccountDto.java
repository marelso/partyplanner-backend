package com.marelso.partyplanner.dto;

import com.marelso.partyplanner.domain.PermissionType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountDto {
    private Integer reference;
    private String username;
    private String email;
    private String name;
    private String lastName;
    private String bio;
    private String profilePicture;
    private PermissionType permissionType;
    private Boolean active;
}
