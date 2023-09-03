package com.marelso.partyplanner.service;

import com.marelso.partyplanner.domain.Account;
import com.marelso.partyplanner.dto.AccountPropertiesDto;
import com.marelso.partyplanner.dto.CreateAccountDto;
import com.marelso.partyplanner.dto.factory.AccountFactory;
import com.marelso.partyplanner.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccountService implements UserDetailsService {
    private final AccountRepository repository;
    private final AccountFactory factory;
    private final S3Service s3Service;

    @Override
    public Account loadUserByUsername(String username) {
        return findUser(username);
    }

    public List<Account> findAll(Boolean includeDeleted) {
        return includeDeleted ? this.repository.findAll()
                : this.repository.findAllByDeletedFalse();
    }

    public Account findUser(String username) {
        return searchUsername(username)
                .orElseThrow(() -> new RuntimeException("Nothing found here."));
    }

    public List<String> findUsernames(List<Integer> accountIds) {
        return repository.findAllById(accountIds).stream().map(Account::getUsername).collect(Collectors.toList());
    }

    public List<Account> validateUsernames(List<String> guests) {
        return guests.stream()
                .map(this::searchUsername)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }

    public Account create(CreateAccountDto request) throws NoSuchAlgorithmException {
        if(isCredentialsInUse(request.getUsername(), request.getEmail()))
            throw new RuntimeException("Credentials already in use.");

        return this.repository.save(factory.from(request));
    }

    public Account update(String username, AccountPropertiesDto request) {
        var thereIsAnyAccount = this.repository.findByUsername(username);
        if(thereIsAnyAccount.isEmpty()) throw new RuntimeException("Account not found");

        return repository.save(factory.from(thereIsAnyAccount.get(), request));
    }

    public Account uploadImage(String username, MultipartFile file) {
        var thereIsAnyAccount = this.repository.findByUsername(username);
        if(thereIsAnyAccount.isEmpty()) throw new RuntimeException("Account not found");

        var current = thereIsAnyAccount.get();

        if(current.getProfilePicture() != null && !current.getProfilePicture().isBlank())
            s3Service.delete(current.getProfilePicture());

        current.setProfilePicture(s3Service.upload(file));

        return current;
    }

    @Transactional
    public void delete(Integer id) {
        this.repository.applySoftDelete(id);
    }

    private Optional<Account> searchUsername(String username) {
        return this.repository.findByUsername(username);
    }

    private Boolean isCredentialsInUse(String username, String email) {
        var isUsernameInUse = this.repository.existsByUsername(username);
        var isEmailInUse = this.repository.existsByEmail(email);

        return isUsernameInUse && isEmailInUse;
    }
}