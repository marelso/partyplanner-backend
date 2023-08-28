package com.marelso.partyplanner.controller;

import com.marelso.partyplanner.domain.Account;
import com.marelso.partyplanner.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/accounts")
public class AccountController {
    private final AccountService service;

    @GetMapping
    public List<Account> get(@RequestParam(required = false, defaultValue = "true") Boolean active) {
        return service.findAll(active);
    }
}