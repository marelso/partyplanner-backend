package com.marelso.partyplanner.service;

import com.marelso.partyplanner.domain.Party;
import com.marelso.partyplanner.dto.PartyCreateDto;
import com.marelso.partyplanner.dto.PartyDto;
import com.marelso.partyplanner.dto.PartyUpdateDto;
import com.marelso.partyplanner.dto.factory.PartyFactory;
import com.marelso.partyplanner.repository.PartyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PartyService {
    private final AccountService accountService;
    private final PartyGuestsService guestService;
    private final PartyRepository repository;
    private final PartyFactory factory;

    public List<PartyDto> list(String username) {
        var account = accountService.findUser(username);

        return factory.from(this.repository.findAllByAccountIdOrderByStartDateAsc(account.getId()), account.getUsername());
    }

    public List<PartyDto> upcoming(String username) {
        var account = accountService.findUser(username);

        return factory.from(this.repository.upcomingParties(account.getId()), account.getUsername());
    }

    public PartyDto create(PartyCreateDto request, String username) {
        areDatesValid(request.getStart(), request.getEnd());

        var account = accountService.findUser(username);
        var party = repository.save(factory.from(request, account.getId()));
        var guests = accountService.validateUsernames(request.getGuests());

        if(!guests.isEmpty())
            guests.forEach(guest -> guestService.inviteUserToParty(guest.getId(), party.getId()));

        return factory.from(party, account.getUsername());
    }

    public PartyDto update(Integer reference, PartyUpdateDto request, String username) {
        areDatesValid(request.getStart(), request.getEnd());

        var account = accountService.findUser(username);
        var party = findPartyById(reference);

        if(!account.getId().equals(party.getAccountId()))
            throw new RuntimeException("You cannot update this party");

        party = this.repository.save(factory.from(party, request));

        return factory.from(party, username);
    }

    private Party findPartyById(Integer id) {
        return searchById(id)
                .orElseThrow(() -> new RuntimeException("Nothing found here."));
    }

    private Optional<Party> searchById(Integer id) {
        return this.repository.findById(id);
    }

    private void areDatesValid(OffsetDateTime start, OffsetDateTime end) {
        if(start.isAfter(end))
            throw new RuntimeException("Invalid dates");
    }
}
