package com.marelso.partyplanner.controller;

import com.marelso.partyplanner.domain.PermissionType;
import com.marelso.partyplanner.dto.PartyCreateDto;
import com.marelso.partyplanner.dto.PartyDto;
import com.marelso.partyplanner.dto.PartyUpdateDto;
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

    @GetMapping("/upcoming")
    public List<PartyDto> upcoming(@RequestHeader("Authorization") String token) {
        var username = authService.authorize(token, PermissionType.USER);

        return service.upcoming(username);
    }

    @PostMapping
    public PartyDto post(@RequestHeader("Authorization") String token, @RequestBody PartyCreateDto request) {
        var username = authService.authorize(token, PermissionType.USER);

        return service.create(request, username);
    }

    @PutMapping
    public PartyDto put(
            @RequestHeader("Authorization") String token,
            @RequestParam Integer reference,
            @RequestBody PartyUpdateDto request) {
        var username = authService.authorize(token, PermissionType.USER);

        return service.update(reference, request, username);
    }
}
