package com.marelso.partyplanner.controller;

import com.marelso.partyplanner.domain.PermissionType;
import com.marelso.partyplanner.dto.PartyCreateDto;
import com.marelso.partyplanner.dto.PartyDto;
import com.marelso.partyplanner.service.AuthService;
import com.marelso.partyplanner.service.PartyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/parties")
public class PartyController {
    private final AuthService authService;
    private final PartyService service;

    @GetMapping
    public List<PartyDto> get(@RequestHeader("Authorization") String token) {
        var username = authService.authorize(token, PermissionType.USER);

        return service.list(username);
    }

    @PostMapping
    public PartyDto create(@RequestHeader("Authorization") String token, @RequestBody PartyCreateDto request) {
        var username = authService.authorize(token, PermissionType.USER);

        return service.create(request, username);
    }
}
