package com.benevolo.service;

import com.benevolo.entity.AddressEntity;
import com.benevolo.entity.EventEntity;
import com.benevolo.repo.AddressRepo;
import com.benevolo.repo.EventRepo;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

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

    public List<EventEntity> findAll() {
        return eventRepo.findAll().stream().toList();
    }

    @Transactional
    public EventEntity save(EventEntity eventEntity, BufferedInputStream image) {
        byte[] imageAsBytes = null;
        try {
            imageAsBytes = image.readAllBytes();
        } catch (IOException e) {
            // TODO: Handle exception
        } catch (Exception e) {
            // TODO: Handle exception
        }
        AddressEntity addressEntity = eventEntity.getAddress();
        addressRepo.persist(addressEntity);
        eventEntity.setPicture(imageAsBytes);
        eventRepo.persist(eventEntity);
        return eventEntity;
    }

    public EventEntity findById(String eventId) {
        return eventRepo.findById(eventId);
    }

    public byte[] getPicture(String eventId) {
        return eventRepo.findById(eventId).getPicture();
    }
}
