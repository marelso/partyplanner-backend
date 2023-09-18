package com.marelso.partyplanner.service;

import com.marelso.partyplanner.repository.PartyGiftRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PartyGiftService {
    private final PartyGiftRepository repository;
}
