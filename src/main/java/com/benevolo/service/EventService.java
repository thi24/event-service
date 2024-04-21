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
import org.jboss.resteasy.reactive.multipart.FileUpload;

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
    public void save(EventDTO eventDTO, byte[] image) {
        EventEntity eventEntity = EventMapper.mapWithoutID(eventDTO);
        AddressEntity addressEntity = eventEntity.getAddress();
        addressRepo.persist(addressEntity);
        System.out.println("EventService.save: image.length = " + image.length);
        eventEntity.setPicture(image);
        eventRepo.persist(eventEntity);
    }

    public EventDTO findById(String eventId) {
        return EventMapper.map(eventRepo.findById(eventId));
    }

}
