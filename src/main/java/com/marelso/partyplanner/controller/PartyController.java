package com.marelso.partyplanner.controller;

import com.marelso.partyplanner.domain.PermissionType;
import com.marelso.partyplanner.dto.*;
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

    @PostMapping("/{id}/invite")
    public PartyDto invite(
            @RequestHeader("Authorization") String token,
            @RequestParam String username,
            @PathVariable Integer id) {
        var account = authService.authorize(token, PermissionType.USER);

        return service.invite(username, id, account);
    }

    @PostMapping("/{id}/uninvite")
    public PartyDto unInvite(
            @RequestHeader("Authorization") String token,
            @RequestParam String username,
            @PathVariable Integer id) {
        var account = authService.authorize(token, PermissionType.USER);

        return service.unInvite(username, id, account);
    }

    @PostMapping("/{id}/gifts")
    public GiftDto post(
            @RequestHeader("Authorization") String token,
            @PathVariable Integer id,
            @RequestBody CreationGiftDto request
    ) {
        var account = authService.authorize(token, PermissionType.USER);
        return service.insertGift(id, account, request);
    }

    @PutMapping("/{id}")
    public PartyDto put(
            @RequestHeader("Authorization") String token,
            @PathVariable Integer id,
            @RequestBody PartyUpdateDto request) {
        var username = authService.authorize(token, PermissionType.USER);

        return service.update(id, request, username);
    }

    @DeleteMapping("/{id}")
    private void delete(
            @RequestHeader("Authorization") String token,
            @PathVariable Integer id
    ) {
        var username = authService.authorize(token, PermissionType.USER);

        service.delete(username, id);
    }

    @DeleteMapping("/{id}/gifts")
    public void remove(
            @RequestHeader("Authorization") String token,
            @PathVariable Integer id,
            @RequestParam Integer request
    ) {
        var account = authService.authorize(token, PermissionType.USER);
        service.removeGift(id, account, request);
    }
}
