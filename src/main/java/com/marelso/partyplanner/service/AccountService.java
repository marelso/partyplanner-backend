package com.marelso.partyplanner.service;

import com.marelso.partyplanner.domain.Account;
import com.marelso.partyplanner.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountService implements UserDetailsService {
    private final AccountRepository repository;

    public List<Account> findAll() {
        return this.repository.findAllByDeletedFalse();
    }

    private Account findUser(String username) {
        return searchUsername(username)
                .orElseThrow(() -> new RuntimeException("There is no account with id: ${id}"));
    }

    private Optional<Account> searchUsername(String username) {
        return this.repository.findByUsername(username);
    }

    @Override
    public Account loadUserByUsername(String username) {
        return findUser(username);
    }
}