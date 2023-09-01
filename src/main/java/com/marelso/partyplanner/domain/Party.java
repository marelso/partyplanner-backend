package com.marelso.partyplanner.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.OffsetDateTime;

@Getter
@Setter
@Entity(name = "parties")
public class Party {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String description;
    private Recurrence recurrence;
    private String bannerURL;
    private OffsetDateTime start;
    private OffsetDateTime end;
}
