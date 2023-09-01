package com.marelso.partyplanner.repository;

import com.marelso.partyplanner.domain.PartyGuest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PartyGuestsRepository extends JpaRepository<PartyGuest, Integer> {
}
