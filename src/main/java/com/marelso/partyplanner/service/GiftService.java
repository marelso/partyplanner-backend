package com.marelso.partyplanner.service;

import com.marelso.partyplanner.domain.Gift;
import com.marelso.partyplanner.dto.CreationGiftDto;
import com.marelso.partyplanner.dto.GiftDto;
import com.marelso.partyplanner.dto.factory.GiftFactory;
import com.marelso.partyplanner.repository.GiftRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GiftService {
    private final GiftRepository repository;
    private final PartyGiftService relation;
    private final GiftFactory factory;

    public GiftDto create(CreationGiftDto request, Integer partyId) {
        var entity = repository.save(factory.from(request));

        relation.includeInParty(entity.getId(), partyId);

        return factory.from(entity);
    }

    public List<GiftDto> list(Integer partyId) {
        return null;
    }

    public void removeFromParty(Integer giftId, Integer partyId) {
        relation.removeFromParty(giftId, partyId);
    }

    public void delete(Integer giftId) {
        relation.delete(giftId);
        repository.deleteById(giftId);
    }
}
