package com.benevolo.service;

import com.benevolo.dto.EventDTO;
import com.benevolo.entity.AddressEntity;
import com.benevolo.entity.EventEntity;
import com.benevolo.mapper.EventMapper;
import com.benevolo.repo.AddressRepo;
import com.benevolo.repo.EventRepo;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;

import java.util.List;


@ApplicationScoped
public class EventService {

    private final EventRepo eventRepo;
    private final AddressRepo addressRepo;

    @Inject
    public EventService(EventRepo eventRepo, AddressRepo addressRepo) {
        this.eventRepo = eventRepo;
        this.addressRepo = addressRepo;
    }

    public List<EventDTO> findAll() {
        return EventMapper.map(eventRepo.findAll());
    }

    public void save(EventDTO eventDTO) {
        EventEntity eventEntity = EventMapper.map(eventDTO);
        System.out.println(eventEntity.getId());
        System.out.println(eventEntity.getAddress().getId());
        AddressEntity addressEntity = eventEntity.getAddress();
        if(eventEntity.getId() == null || eventEntity.getId().isBlank()) {
            eventEntity.setId(java.util.UUID.randomUUID().toString());
        }
        addressRepo.save(addressEntity);
        eventRepo.save(eventEntity);
    }

    public EventDTO findById(String eventId) {
        return EventMapper.map(eventRepo.findById(eventId).orElseThrow(NotFoundException::new));
    }

}
