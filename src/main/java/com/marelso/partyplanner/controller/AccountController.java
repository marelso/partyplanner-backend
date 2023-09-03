package com.marelso.partyplanner.controller;

import com.marelso.partyplanner.domain.PermissionType;
import com.marelso.partyplanner.dto.AccountDto;
import com.marelso.partyplanner.dto.AccountPropertiesDto;
import com.marelso.partyplanner.dto.CreateAccountDto;
import com.marelso.partyplanner.dto.factory.AccountFactory;
import com.marelso.partyplanner.service.AccountService;
import com.marelso.partyplanner.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/accounts")
public class AccountController {
    private final AccountService service;
    private final AccountFactory factory;
    private final AuthService authService;

    @GetMapping
    public List<AccountDto> get(
            @RequestHeader("Authorization") String token,
            @RequestParam(required = false, defaultValue = "false") Boolean includeDeleted
    ) {
        authService.authorize(token, PermissionType.MANAGER);
        return factory.from(service.findAll(includeDeleted));
    }

    @PostMapping
    public AccountDto post(@RequestBody CreateAccountDto request) throws NoSuchAlgorithmException {
        return factory.from(service.create(request));
    }

    @PutMapping("/{id}")
    public AccountDto put(
            @RequestHeader("Authorization") String token,
            @RequestBody AccountPropertiesDto request) {
        var account = authService.authorize(token, PermissionType.USER);

        return this.factory.from(this.service.update(account, request));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        this.service.delete(id);
    }
}