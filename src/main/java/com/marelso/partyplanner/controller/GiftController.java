package com.marelso.partyplanner.controller;

import com.marelso.partyplanner.domain.PermissionType;
import com.marelso.partyplanner.dto.GiftDto;
import com.marelso.partyplanner.service.AuthService;
import com.marelso.partyplanner.service.GiftService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequiredArgsConstructor
@RequestMapping("/gifts")
public class GiftController {
    private final GiftService service;
    private final AuthService authService;

    @GetMapping
    private Page<GiftDto> get(
            @RequestHeader("Authorization") String token,
            @RequestParam Integer partyId,
            @ApiIgnore Pageable pageable
            ) {
        authService.authorize(token, PermissionType.USER);
        return service.list(partyId, pageable);
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
