package com.marelso.partyplanner.service;

import com.marelso.partyplanner.dto.PartyCreateDto;
import com.marelso.partyplanner.dto.PartyDto;
import com.marelso.partyplanner.dto.factory.PartyFactory;
import com.marelso.partyplanner.repository.PartyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PartyService {
    private final AccountService accountService;
    private final PartyRepository repository;
    private final PartyFactory factory;

    public List<PartyDto> list(String username) {
        var account = accountService.findUser(username);

        return factory.from(this.repository.findAllByAccountIdOrderByStartDateAsc(account.getId()), account.getUsername());
    }

    public PartyDto create(PartyCreateDto request, String username) {
        validateRequest(request);

        var account = accountService.findUser(username);
        var party = repository.save(factory.from(request, account.getId()));

        return factory.from(party, account.getUsername());
    }

    private void validateRequest(PartyCreateDto request) {
        areDatesValid(request.getStart(), request.getEnd());
    }

    private void areDatesValid(OffsetDateTime start, OffsetDateTime end) {
        if(start.isAfter(end))
            throw new RuntimeException("Invalid dates");
    }
}
