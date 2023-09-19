package com.marelso.partyplanner.service;

import com.marelso.partyplanner.domain.Account;
import com.marelso.partyplanner.domain.Party;
import com.marelso.partyplanner.dto.*;
import com.marelso.partyplanner.dto.factory.PartyFactory;
import com.marelso.partyplanner.repository.PartyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PartyService {
    private final AccountService accountService;
    private final PartyGuestsService guestService;
    private final PartyGiftService relationService;
    private final PartyRepository repository;
    private final GiftService giftService;
    private final PartyFactory factory;

    public Page<PartyDto> list(String username, Pageable pageable) {
        var account = accountService.findUser(username);
        var parties = this.repository.findAllByAccountIdOrderByStartDateAsc(account.getId(), pageable);

        return parties.map(party ->  {
                    var usernames = accountService.findUsernames(guestService.findGuestsByPartyId(party.getId()));
                    return factory.from(party, account.getUsername(), usernames);
                });
    }

    public Page<PartyDto> upcoming(String username, Pageable pageable) {
        var account = accountService.findUser(username);
        var parties = this.repository.upcomingParties(account.getId(), pageable);

        return parties.map(party ->  {
                    var usernames = accountService.findUsernames(guestService.findGuestsByPartyId(party.getId()));
                    return factory.from(party, account.getUsername(), usernames);
                }
        );
    }

    public Page<PartyDto> invited(String username, Pageable pageable) {
        var account = accountService.findUser(username);
        var parties = guestService.findPartiesByGuestId(account.getId());

        return repository.findByIdIn(parties, pageable).map(party -> {
            var usernames = accountService.findUsernames(guestService.findGuestsByPartyId(party.getId()));
            return factory.from(party, account.getUsername(), usernames);
        });
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

    public GiftDto insertGift(Integer partyId, String username, CreationGiftDto request) {
        var account = accountService.findUser(username);
        var party = findPartyById(partyId);

        accountCanApplyChanges(account, party);

        return giftService.create(request, partyId);
    }

    public void removeGift(Integer partyId, String username, Integer giftId) {
        var account = accountService.findUser(username);
        var party = findPartyById(partyId);

        accountCanApplyChanges(account, party);

        giftService.delete(giftId);
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

    public PartyDto unInvite(String invite, Integer id, String username) {
        var account = accountService.findUser(username);
        var guest = accountService.findUser(invite);
        var party = findPartyById(id);

        accountCanApplyChanges(account, party);
        guestService.unInviteUserFromParty(guest.getId(), party.getId());

        var usernames = accountService.findUsernames(guestService.findGuestsByPartyId(party.getId()));

        return factory.from(party, account.getUsername(), usernames);
    }

    @Transactional
    public void delete(String username, Integer id) {
        var account = accountService.findUser(username);
        var party = findPartyById(id);

        accountCanApplyChanges(account, party);

        var gifts = relationService.getGiftIdsFromParty(id);
        gifts.forEach(giftService::delete);

        repository.delete(party);
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
