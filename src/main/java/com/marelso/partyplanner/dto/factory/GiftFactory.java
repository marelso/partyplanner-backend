package com.marelso.partyplanner.dto.factory;

import com.marelso.partyplanner.domain.Gift;
import com.marelso.partyplanner.dto.CreationGiftDto;
import com.marelso.partyplanner.dto.GiftDto;
import org.springframework.stereotype.Component;

@Component
public class GiftFactory {
    public Gift from(CreationGiftDto dto) {
        var entity = new Gift();

        entity.setTitle(dto.getTitle());
        entity.setDescription(dto.getDescription());
        entity.setImage(dto.getImage());
        entity.setLinks(dto.getLinks());

        return entity;
    }

    public GiftDto from(Gift entity) {
        var dto = new GiftDto();

        dto.setReference(entity.getId());
        dto.setTitle(entity.getTitle());
        dto.setDescription(entity.getDescription());
        dto.setImage(entity.getImage());
        dto.setLinks(entity.getLinks());

        return dto;
    }
}
