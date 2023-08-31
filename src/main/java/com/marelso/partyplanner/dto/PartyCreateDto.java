package com.marelso.partyplanner.dto;

import com.marelso.partyplanner.domain.Recurrence;

import java.time.OffsetDateTime;

public class PartyCreateDto {
    private String name;
    private String description;
    private Integer createdBy;
    private Recurrence recurrence;
    private OffsetDateTime start;
    private OffsetDateTime end;
}
