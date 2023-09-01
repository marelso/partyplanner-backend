package com.marelso.partyplanner.controller;

import com.marelso.partyplanner.domain.PermissionType;
import com.marelso.partyplanner.dto.PartyCreateDto;
import com.marelso.partyplanner.dto.PartyDto;
import com.marelso.partyplanner.dto.factory.PartyFactory;
import com.marelso.partyplanner.service.AuthService;
import com.marelso.partyplanner.service.PartyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/parties")
public class PartyController {
    private final AuthService authService;
    private final PartyService service;
    private final PartyFactory factory;

    @PostMapping
    public PartyDto create(@RequestHeader("Authorization") String token, @RequestBody PartyCreateDto request) {
        authService.authorize(token, PermissionType.USER);

        return factory.from(service.create(request));
    }
}
