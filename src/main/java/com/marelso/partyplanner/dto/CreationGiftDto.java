package com.marelso.partyplanner.dto;

import com.marelso.partyplanner.domain.Link;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CreationGiftDto {
    private String title;
    private String description;
    private String image;
    private List<Link> links;
}
