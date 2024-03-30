package com.benevolo.service;

import com.benevolo.dto.EventDTO;
import com.benevolo.entity.EventEntity;
import com.benevolo.mapper.EventMapper;
import com.benevolo.repo.EventRepo;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityResult;
import jakarta.ws.rs.NotFoundException;

import java.util.List;

@ApplicationScoped
public class EventService {

    private final EventRepo eventRepo;

    @Inject
    public EventService(EventRepo eventRepo) {
        this.eventRepo = eventRepo;
    }

    public List<EventDTO> findAll() {
        return EventMapper.map(eventRepo.findAll());
    }

    public void save(EventDTO eventDTO) {
        eventRepo.save(new EventEntity(eventDTO.name(), eventDTO.startsAt(), eventDTO.endsAt()));
    }

    public EventDTO findById(String eventId) {
        return EventMapper.map(eventRepo.findById(eventId).orElseThrow(() -> new NotFoundException()));
    }

}
