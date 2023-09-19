package com.marelso.partyplanner.service;

import com.marelso.partyplanner.domain.Link;
import com.marelso.partyplanner.repository.LinkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LinkService {
    private final LinkRepository repository;

    public List<Link> create(List<Link> links) {
        return repository.saveAll(links);
    }

    public void delete(List<Link> links) {
        repository.deleteAll(links);
    }
}
