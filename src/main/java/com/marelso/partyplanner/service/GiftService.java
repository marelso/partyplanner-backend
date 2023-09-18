package com.marelso.partyplanner.service;

import com.marelso.partyplanner.domain.Gift;
import com.marelso.partyplanner.dto.CreationGiftDto;
import com.marelso.partyplanner.dto.factory.GiftFactory;
import com.marelso.partyplanner.repository.GiftRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GiftService {
    private final GiftRepository repository;
    private final PartyGiftService relation;
    private final GiftFactory factory;

    public Gift create(CreationGiftDto request, Integer partyId) {
        var entity = repository.save(factory.from(request));

        relation.includeInParty(entity.getId(), partyId);

        return entity;
    }
}
