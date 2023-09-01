package com.marelso.partyplanner.service;

import com.marelso.partyplanner.domain.PartyGuest;
import com.marelso.partyplanner.repository.PartyGuestsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PartyGuestsService {
    private final PartyGuestsRepository repository;

    public void inviteUserToParty(Integer account, Integer party) {
        var guest = new PartyGuest();

        guest.setPartyId(party);
        guest.setAccountId(account);

        repository.save(guest);
    }
}
