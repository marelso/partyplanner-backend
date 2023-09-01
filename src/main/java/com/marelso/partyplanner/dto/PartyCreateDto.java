package com.marelso.partyplanner.dto;

import com.marelso.partyplanner.domain.Recurrence;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.List;

@Getter
@Setter
public class PartyCreateDto {
    private String name;
    private String description;
    private Recurrence recurrence;
    private List<String> guests;
    private OffsetDateTime start;
    private OffsetDateTime end;
}
