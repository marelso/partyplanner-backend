package com.marelso.partyplanner.dto.factory;

import com.marelso.partyplanner.domain.Account;
import com.marelso.partyplanner.domain.PermissionType;
import com.marelso.partyplanner.dto.AccountDto;
import com.marelso.partyplanner.dto.CreateAccountDto;
import com.marelso.partyplanner.service.EncryptionService;
import org.springframework.stereotype.Component;

import java.security.NoSuchAlgorithmException;

@Component
public class AccountFactory {
    public Account from(CreateAccountDto dto) throws NoSuchAlgorithmException {
        var account = new Account();

        account.setUsername(dto.getUsername());
        account.setEmail(dto.getEmail());
        account.setName(dto.getName());
        account.setLastName(dto.getLastName());
        account.setPassword(EncryptionService.encrypt(dto.getPassword()));

        return account;
    }

    public AccountDto from(Account entity) {
        var dto = new AccountDto();

        dto.setReference(entity.getId());
        dto.setUsername(entity.getUsername());
        dto.setEmail(entity.getEmail());
        dto.setName(entity.getName());
        dto.setLastName(entity.getLastName());
        dto.setProfilePicture(entity.getProfilePicture());
        dto.setActive(!entity.isDeleted());

        return dto;
    }
}
