package com.marelso.partyplanner.repository;

import com.marelso.partyplanner.domain.Party;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PartyRepository extends JpaRepository<Party, Integer> {
    List<Party> findAllByAccountIdOrderByStartDateAsc(Integer accountId);
}
