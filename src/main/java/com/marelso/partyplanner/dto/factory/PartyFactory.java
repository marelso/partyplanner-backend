package com.marelso.partyplanner.dto.factory;

import com.marelso.partyplanner.domain.Account;
import com.marelso.partyplanner.domain.Party;
import com.marelso.partyplanner.dto.PartyCreateDto;
import com.marelso.partyplanner.dto.PartyDto;
import com.marelso.partyplanner.dto.PartyUpdateDto;
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
        entity.setStartDate(dto.getStart());
        entity.setEndDate(dto.getEnd());

        return entity;
    }

    public PartyDto from(Party entity, String username) {
        var dto = new PartyDto();

        dto.setReference(entity.getId());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        dto.setRecurrence(entity.getRecurrence());
        dto.setCreatedBy(username);
        dto.setStart(entity.getStartDate());
        dto.setEnd(entity.getEndDate());

        return dto;
    }

    public List<PartyDto> from(List<Party> parties, String username) {
        return parties.stream().map(party -> from(party, username)).collect(Collectors.toList());
    }

    public Party from(Party party, PartyUpdateDto request) {
        party.setName(request.getName());
        party.setDescription(request.getDescription());
        party.setRecurrence(request.getRecurrence());
        party.setStartDate(request.getStart());
        party.setEndDate(request.getEnd());

        return party;
    }
}
