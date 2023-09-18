package com.marelso.partyplanner.service;

import com.marelso.partyplanner.domain.PartyGift;
import com.marelso.partyplanner.repository.PartyGiftRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PartyGiftService {
    private final PartyGiftRepository repository;

    public void includeInParty(Integer gift, Integer party) {
        if(relationDoNotExists(gift, party)) {
            var entity = new PartyGift();
            entity.setPartyId(party);
            entity.setGiftId(gift);
            repository.save(entity);
        }
    }

    public void removeFromParty(Integer gift, Integer party) {
        var entity = repository.findByPartyIdAndGiftId(party, gift);
        entity.ifPresent((p) -> repository.deleteById(p.getId()));
    }

    public void delete(Integer gift) {
        repository.deleteAllByGiftId(gift);
    }

    private Boolean relationDoNotExists(Integer giftId, Integer partyId) {
        return !repository.existsByPartyIdAndGiftId(partyId, giftId);
    }
}
