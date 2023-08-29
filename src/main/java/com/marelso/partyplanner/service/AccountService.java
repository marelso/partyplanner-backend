package com.marelso.partyplanner.service;

import com.marelso.partyplanner.domain.Account;
import com.marelso.partyplanner.dto.CreateAccountDto;
import com.marelso.partyplanner.dto.factory.AccountFactory;
import com.marelso.partyplanner.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountService implements UserDetailsService {
    private final AccountRepository repository;
    private final AccountFactory factory;

    public List<Account> findAll(Boolean active) {
        return active ? this.repository.findAllByDeletedFalse()
                : this.repository.findAll();
    }

    public Account findUser(String username) {
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

    public Account create(CreateAccountDto request) throws NoSuchAlgorithmException {
        if(isCredentialsInUse(request.getUsername(), request.getEmail()))
            throw new RuntimeException("Credentials already in use.");

        return this.repository.save(factory.from(request));
    }

    private Boolean isCredentialsInUse(String username, String email) {
        var isUsernameInUse = this.repository.existsByUsername(username);
        var isEmailInUse = this.repository.existsByEmail(email);

        return isUsernameInUse && isEmailInUse;
    }

}