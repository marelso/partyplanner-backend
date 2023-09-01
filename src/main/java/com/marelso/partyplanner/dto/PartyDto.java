package com.marelso.partyplanner.dto;

import com.marelso.partyplanner.domain.Recurrence;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.List;

@Getter
@Setter
public class PartyDto {
    private Integer reference;
    private String name;
    private String description;
    private String createdBy;
    private List<String> guests;
    private Recurrence recurrence;
    private OffsetDateTime start;
    private OffsetDateTime end;
}
