package com.benevolo.service;

import com.benevolo.dto.EventDTO;
import com.benevolo.entity.AddressEntity;
import com.benevolo.entity.EventEntity;
import com.benevolo.mapper.EventMapper;
import com.benevolo.repo.AddressRepo;
import com.benevolo.repo.EventRepo;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.WebApplicationException;

import java.io.BufferedInputStream;
import java.io.IOException;
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
        return EventMapper.map(eventRepo.findAll().stream().toList());
    }

    @Transactional
    public EventDTO save(EventDTO eventDTO, BufferedInputStream image) {
        byte[] imageAsBytes = null;
        try {
            imageAsBytes = image.readAllBytes();
        } catch (IOException e) {
            // TODO: Handle exception
        } catch (Exception e) {
            // TODO: Handle exception
        }
        EventEntity eventEntity = EventMapper.mapWithoutID(eventDTO);
        AddressEntity addressEntity = eventEntity.getAddress();
        addressRepo.persist(addressEntity);
        eventEntity.setPicture(imageAsBytes);
        eventRepo.persist(eventEntity);
        return EventMapper.map(eventEntity);
    }

    public EventDTO findById(String eventId) {
        return EventMapper.map(eventRepo.findById(eventId));
    }

    public byte[] getPicture(String eventId) {
        return eventRepo.findById(eventId).getPicture();
    }
}
