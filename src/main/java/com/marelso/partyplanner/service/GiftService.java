package com.marelso.partyplanner.service;

import com.marelso.partyplanner.domain.Gift;
import com.marelso.partyplanner.dto.CreationGiftDto;
import com.marelso.partyplanner.dto.GiftDto;
import com.marelso.partyplanner.dto.factory.GiftFactory;
import com.marelso.partyplanner.repository.GiftRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GiftService {
    private final GiftRepository repository;
    private final PartyGiftService relation;
    private final LinkService linkService;
    private final GiftFactory factory;

    public GiftDto create(CreationGiftDto request, Integer partyId) {
        request.setLinks(linkService.create(request.getLinks()));

        var entity = repository.save(factory.from(request));

        relation.includeInParty(entity.getId(), partyId);

        return factory.from(entity);
    }

    public Page<GiftDto> list(Integer partyId, Pageable pageable) {
        var partyGiftIds = relation.getGiftIdsFromParty(partyId);

        return repository.findAllByIdIn(partyGiftIds, pageable).map(factory::from);
    }

    public void removeFromParty(Integer giftId, Integer partyId) {
        relation.removeFromParty(giftId, partyId);
    }

    @Transactional
    public void delete(Integer giftId) {
        findById(giftId).ifPresent((gift -> {
            relation.delete(gift.getId());
            linkService.delete(gift.getLinks());
            repository.deleteById(gift.getId());
        }));
    }

    private Gift get(Integer giftId) {
        return findById(giftId)
                .orElseThrow(() -> new RuntimeException("Nothing found here."));
    }

    private Optional<Gift> findById(Integer giftId) {
        return repository.findById(giftId);
    }
}
