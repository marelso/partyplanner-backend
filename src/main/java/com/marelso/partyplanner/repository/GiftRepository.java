package com.marelso.partyplanner.repository;

import com.marelso.partyplanner.domain.Gift;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GiftRepository extends JpaRepository<Gift, Integer> {
    Page<Gift> findAllByIdIn(List<Integer> id, Pageable pageable);
}
