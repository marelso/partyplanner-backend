package com.marelso.partyplanner.controller;

import com.marelso.partyplanner.domain.PermissionType;
import com.marelso.partyplanner.dto.GiftDto;
import com.marelso.partyplanner.service.AuthService;
import com.marelso.partyplanner.service.GiftService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/gifts")
public class GiftController {
    private final GiftService service;
    private final AuthService authService;

    @GetMapping
    private List<GiftDto> get(
            @RequestHeader("Authorization") String token,
            @RequestParam Integer partyId
    ) {
        authService.authorize(token, PermissionType.USER);
        return service.list(partyId);
    }

    @DeleteMapping("/{id}")
    public void delete(
            @RequestHeader("Authorization") String token,
            @PathVariable Integer id
    ) {
        authService.authorize(token, PermissionType.USER);
        service.delete(id);
    }
}
