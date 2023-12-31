package com.marelso.partyplanner.repository;

import com.marelso.partyplanner.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {
    List<Account> findAllByDeletedFalse();
    Optional<Account> findByUsername(String username);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
    @Modifying
    @Query("UPDATE accounts acc SET acc.deleted = true WHERE acc.id = ?1")
    void applySoftDelete(Integer id);
}
