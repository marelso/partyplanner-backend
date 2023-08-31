package com.marelso.partyplanner.service;

import com.marelso.partyplanner.domain.Party;
import com.marelso.partyplanner.dto.PartyCreateDto;
import com.marelso.partyplanner.dto.factory.PartyFactory;
import com.marelso.partyplanner.repository.PartyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PartyService {
    private final AccountService accountService;
    private final PartyRepository repository;
    private final PartyFactory factory;

    public Party create(PartyCreateDto request) {
        var account = accountService.findUser(request.getCreatedBy());
        return repository.save(factory.from(request, account));
    }
}
