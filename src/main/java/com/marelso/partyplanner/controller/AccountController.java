package com.marelso.partyplanner.controller;

import com.marelso.partyplanner.domain.Account;
import com.marelso.partyplanner.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("accounts")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService service;

    @GetMapping
    public List<Account> get() {
        return service.findAll();
    }

}