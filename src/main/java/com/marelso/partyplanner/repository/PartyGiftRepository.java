package com.marelso.partyplanner.repository;

import com.marelso.partyplanner.domain.PartyGift;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PartyGiftRepository extends JpaRepository<PartyGift, Integer> {
    @Query(value = "select gift_id from party_gifts pg where pg.party_id = :partyId", nativeQuery = true)
    List<Integer> findGiftsIdsByPartyId(@Param("partyId") Integer partyId);
    Boolean existsByPartyIdAndGiftId(Integer partyId, Integer giftId);
    Optional<PartyGift> findByPartyIdAndGiftId(Integer partyId, Integer giftId);
    void deleteAllByGiftId(Integer giftId);
}
