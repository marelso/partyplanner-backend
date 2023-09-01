package com.marelso.partyplanner.repository;

import com.marelso.partyplanner.domain.Party;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PartyRepository extends JpaRepository<Party, Integer> {
    List<Party> findAllByAccountIdOrderByStartDateAsc(Integer accountId);

    @Query(value = "SELECT * FROM parties p " +
            "WHERE p.account_id = :accountId " +
            "AND p.start_date >= now() and p.start_date <= now()+ interval '6 day' " +
            "AND p.end_date > now() " +
            "ORDER BY p.start_date ASC " , nativeQuery = true)
    List<Party> upcomingParties(@Param("accountId") Integer accountId);
}
