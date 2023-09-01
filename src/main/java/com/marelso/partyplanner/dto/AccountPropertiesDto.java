package com.marelso.partyplanner.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class AccountPropertiesDto {
    private String name;
    private String lastName;
    private String bio;
    private MultipartFile profilePicture;
}
