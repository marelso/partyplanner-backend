package com.marelso.partyplanner.service;

import com.marelso.partyplanner.domain.Account;
import com.marelso.partyplanner.dto.AccountDto;
import com.marelso.partyplanner.dto.AccountPropertiesDto;
import com.marelso.partyplanner.dto.CreateAccountDto;
import com.marelso.partyplanner.dto.factory.AccountFactory;
import com.marelso.partyplanner.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccountService implements UserDetailsService {
    private final AccountRepository repository;
    private final AccountFactory factory;

    public List<Account> findAll(Boolean includeDeleted) {
        return includeDeleted ? this.repository.findAll()
                : this.repository.findAllByDeletedFalse();
    }

    public Account findUser(String username) {
        return searchUsername(username)
                .orElseThrow(() -> new RuntimeException("Nothing found here."));
    }

    public List<Account> validateUsernames(List<String> guests) {
        return guests.stream()
                .map(this::searchUsername)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
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

    public Account update(Integer id, AccountPropertiesDto request) {
        var thereIsAnyAccount = this.repository.findById(id);
        if(thereIsAnyAccount.isEmpty()) throw new RuntimeException("Account not found");

        //TODO create s3service and handle profile image changes (upload/delete)
        var pp = "";
        var account = factory.from(thereIsAnyAccount.get(), request, pp);

        return repository.save(account);
    }

    @Transactional
    public void delete(Integer id) {
        this.repository.applySoftDelete(id);
    }
}