package com.marelso.partyplanner.repository;

import com.marelso.partyplanner.domain.Gift;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GiftRepository extends JpaRepository<Gift, Integer> {
}
