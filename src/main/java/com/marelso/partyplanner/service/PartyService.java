package com.marelso.partyplanner.service;

import com.marelso.partyplanner.repository.PartyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PartyService {
    private final PartyRepository repository;
}
