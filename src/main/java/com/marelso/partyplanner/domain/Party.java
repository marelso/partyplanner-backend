package com.marelso.partyplanner.domain;

import com.marelso.partyplanner.dto.AccountDto;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.OffsetDateTime;

public class Party {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String description;
    private Recurrence recurrence;
    private String bannerURL;
    private Account createdBy;
    private OffsetDateTime start;
    private OffsetDateTime end;
}
