package com.marelso.partyplanner.repository;

import com.marelso.partyplanner.domain.PartyGuest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PartyGuestsRepository extends JpaRepository<PartyGuest, Integer> {
    List<PartyGuest> findAllByPartyId(Integer partyId);
    @Query(value = "select pg.party_id from party_guests pg where pg.account_id = :accountId", nativeQuery = true)
    List<Integer> findPartyIdByAccountId(@Param("accountId") Integer accountId);
    @Query(value = "select * from party_guests guest where guest.account_id = :accountId and guest.party_id = :partyId", nativeQuery = true)
    Optional<PartyGuest> findByPartyIdAndAccountId(@Param("accountId") Integer accountId, @Param("partyId") Integer partyId);
}