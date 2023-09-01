package com.marelso.partyplanner.service;

import com.marelso.partyplanner.domain.Account;
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
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PartyService {
    private final AccountService accountService;
    private final PartyGuestsService guestService;
    private final PartyRepository repository;
    private final PartyFactory factory;

    public List<PartyDto> list(String username) {
        var account = accountService.findUser(username);
        var parties = this.repository.findAllByAccountIdOrderByStartDateAsc(account.getId());

        return parties.stream().map(party ->  {
                    var usernames = accountService.findUsernames(guestService.findGuestsByPartyId(party.getId()));
                    return factory.from(party, account.getUsername(), usernames);
                }
        ).collect(Collectors.toList());
    }

    public List<PartyDto> upcoming(String username) {
        var account = accountService.findUser(username);
        var parties = this.repository.upcomingParties(account.getId());

        return parties.stream().map(party ->  {
                    var usernames = accountService.findUsernames(guestService.findGuestsByPartyId(party.getId()));
                    return factory.from(party, account.getUsername(), usernames);
                }
        ).collect(Collectors.toList());
    }

    public PartyDto create(PartyCreateDto request, String username) {
        areDatesValid(request.getStart(), request.getEnd());

        var account = accountService.findUser(username);
        var party = repository.save(factory.from(request, account.getId()));
        var guests = accountService.validateUsernames(request.getGuests());

        if(!guests.isEmpty())
            guests.forEach(guest -> guestService.inviteUserToParty(guest.getId(), party.getId()));

        var guestsNames = accountService.findUsernames(guestService.findGuestsByPartyId(party.getId()));

        return factory.from(party, account.getUsername(), guestsNames);
    }

    public PartyDto update(Integer reference, PartyUpdateDto request, String username) {
        areDatesValid(request.getStart(), request.getEnd());

        var account = accountService.findUser(username);
        var party = findPartyById(reference);

        accountCanApplyChanges(account, party);

        party = this.repository.save(factory.from(party, request));
        var guestNames = accountService.findUsernames(guestService.findGuestsByPartyId(party.getId()));

        return factory.from(party, username, guestNames);
    }

    public PartyDto invite(String invite, Integer id, String username) {
        var account = accountService.findUser(username);
        var guest = accountService.findUser(invite);
        var party = findPartyById(id);

        accountCanApplyChanges(account, party);
        guestService.inviteUserToParty(guest.getId(), party.getId());

        var usernames = accountService.findUsernames(guestService.findGuestsByPartyId(party.getId()));

        return factory.from(party, account.getUsername(), usernames);
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
    private void accountCanApplyChanges(Account account, Party party) {
        if(!account.getId().equals(party.getAccountId()))
            throw new RuntimeException("You cannot update this party");
    }
}
