package com.marelso.partyplanner.service;

import com.marelso.partyplanner.dto.PartyCreateDto;
import com.marelso.partyplanner.dto.PartyDto;
import com.marelso.partyplanner.dto.factory.PartyFactory;
import com.marelso.partyplanner.repository.PartyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PartyService {
    private final AccountService accountService;
    private final PartyRepository repository;
    private final PartyFactory factory;

    public List<PartyDto> list(String username) {
        var account = accountService.findUser(username);

        return factory.from(this.repository.findAllByAccountId(account.getId()), account.getUsername());
    }

    public PartyDto create(PartyCreateDto request, String username) {
        var account = accountService.findUser(username);
        var party = repository.save(factory.from(request, account.getId()));

        return factory.from(party, account.getUsername());
    }
}
