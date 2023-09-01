package com.marelso.partyplanner.dto.factory;

import com.marelso.partyplanner.domain.Account;
import com.marelso.partyplanner.domain.Party;
import com.marelso.partyplanner.dto.PartyCreateDto;
import com.marelso.partyplanner.dto.PartyDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PartyFactory {
    public Party from(PartyCreateDto dto, Integer accountId) {
        var entity = new Party();

        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setRecurrence(dto.getRecurrence());
        entity.setAccountId(accountId);
        entity.setStart(dto.getStart());
        entity.setEnd(dto.getEnd());

        return entity;
    }

    public PartyDto from(Party entity, String username) {
        var dto = new PartyDto();

        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        dto.setRecurrence(entity.getRecurrence());
        dto.setCreatedBy(username);
        dto.setStart(entity.getStart());
        dto.setEnd(entity.getEnd());

        return dto;
    }

    public List<PartyDto> from(List<Party> parties, String username) {
        return parties.stream().map(party -> from(party, username)).collect(Collectors.toList());
    }
}
