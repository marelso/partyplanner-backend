package com.marelso.partyplanner.controller;

import com.marelso.partyplanner.dto.CreationGiftDto;
import com.marelso.partyplanner.dto.GiftDto;
import com.marelso.partyplanner.service.GiftService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/gifts")
public class GiftController {
    private final GiftService service;

    @PostMapping
    public GiftDto post(
            @RequestParam Integer partyId,
            @RequestBody CreationGiftDto request
    ) {
        return service.create(request, partyId);
    }
}
