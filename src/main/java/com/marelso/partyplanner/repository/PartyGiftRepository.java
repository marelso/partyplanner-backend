package com.marelso.partyplanner.repository;

import com.marelso.partyplanner.domain.PartyGift;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PartyGiftRepository extends JpaRepository<PartyGift, Integer> {
    Boolean existsByPartyIdAndGiftId(Integer partyId, Integer giftId);
    void deleteAllByGiftId(Integer giftId);
}
