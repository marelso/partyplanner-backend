package com.marelso.partyplanner.controller;

import com.marelso.partyplanner.domain.PermissionType;
import com.marelso.partyplanner.dto.CreationGiftDto;
import com.marelso.partyplanner.dto.GiftDto;
import com.marelso.partyplanner.service.AuthService;
import com.marelso.partyplanner.service.GiftService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/gifts")
public class GiftController {
    private final GiftService service;
    private final AuthService authService;

    @PostMapping
    public GiftDto post(
            @RequestHeader("Authorization") String token,
            @RequestParam Integer partyId,
            @RequestBody CreationGiftDto request
    ) {
        authService.authorize(token, PermissionType.USER);
        return service.create(request, partyId);
    }
}
