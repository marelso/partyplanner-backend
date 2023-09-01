package com.marelso.partyplanner.service;

import com.marelso.partyplanner.domain.PartyGuest;
import com.marelso.partyplanner.repository.PartyGuestsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PartyGuestsService {
    private final PartyGuestsRepository repository;

    public void inviteUserToParty(Integer account, Integer party) {
        var isUserAlreadyInvited = repository.findByPartyAndAccountId(account, party);

        if(isUserAlreadyInvited.isEmpty()) {
            var guest = new PartyGuest();

            guest.setPartyId(party);
            guest.setAccountId(account);

            repository.save(guest);
        }
    }

    public void unInviteUserFromParty(Integer account, Integer party) {
        repository.findByPartyAndAccountId(account, party).ifPresent(repository::delete);
    }

    public List<Integer> findGuestsByPartyId(Integer id) {
        return repository.findAllByPartyId(id)
                .stream()
                .map(PartyGuest::getAccountId)
                .collect(Collectors.toList());
    }
}
