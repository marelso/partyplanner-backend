package com.marelso.partyplanner.controller;

import com.marelso.partyplanner.domain.Account;
import com.marelso.partyplanner.dto.AccountDto;
import com.marelso.partyplanner.dto.CreateAccountDto;
import com.marelso.partyplanner.dto.factory.AccountFactory;
import com.marelso.partyplanner.service.AccountService;
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

    @GetMapping
    public List<AccountDto> get(@RequestParam(required = false, defaultValue = "false") Boolean includeDeleted) {
        return factory.from(service.findAll(includeDeleted));
    }

    @PostMapping
    public AccountDto post(@RequestBody CreateAccountDto request) throws NoSuchAlgorithmException {
        return factory.from(service.create(request));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        this.service.delete(id);
    }
}