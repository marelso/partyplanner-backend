package com.marelso.partyplanner.service;

import com.marelso.partyplanner.domain.Gift;
import com.marelso.partyplanner.dto.CreationGiftDto;
import com.marelso.partyplanner.dto.factory.GiftFactory;
import com.marelso.partyplanner.repository.GiftRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GiftService {
    private final GiftRepository repository;
    private final GiftFactory factory;

    public Gift create(CreationGiftDto request) {
        return repository.save(factory.from(request));
    }
}
