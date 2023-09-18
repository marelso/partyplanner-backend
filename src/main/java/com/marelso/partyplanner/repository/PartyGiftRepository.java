package com.marelso.partyplanner.repository;

import com.marelso.partyplanner.domain.PartyGifts;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PartyGiftRepository extends JpaRepository<PartyGifts, Integer> {
}
