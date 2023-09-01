package com.marelso.partyplanner.dto;

import com.marelso.partyplanner.domain.Recurrence;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
public class PartyUpdateDto {
    private String name;
    private String description;
    private Recurrence recurrence;
    private OffsetDateTime start;
    private OffsetDateTime end;
}
