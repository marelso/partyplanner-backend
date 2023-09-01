package com.marelso.partyplanner.service;

import com.marelso.partyplanner.repository.PartyGuestsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PartyGuestsService {
    private final PartyGuestsRepository repository;
}
