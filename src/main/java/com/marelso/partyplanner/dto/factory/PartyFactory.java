package com.marelso.partyplanner.dto.factory;

import com.marelso.partyplanner.domain.Account;
import com.marelso.partyplanner.domain.Party;
import com.marelso.partyplanner.dto.PartyCreateDto;
import org.springframework.stereotype.Component;

@Component
public class PartyFactory {
    public Party from(PartyCreateDto dto, Account createdBy) {
        var entity = new Party();

        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setRecurrence(dto.getRecurrence());
        entity.setCreatedBy(createdBy);
        entity.setStart(dto.getStart());
        entity.setEnd(dto.getEnd());

        return entity;
    }
}
