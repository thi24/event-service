package com.benevolo.service;

import com.benevolo.client.ProcessEngineClient;
import com.benevolo.entity.AddressEntity;
import com.benevolo.entity.EventEntity;
import com.benevolo.repo.AddressRepo;
import com.benevolo.repo.EventRepo;
import io.quarkus.panache.common.Parameters;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.WebApplicationException;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.io.BufferedInputStream;
import java.util.List;

@ApplicationScoped
public class EventService {

    @Inject
    EventRepo eventRepo;

    @Inject
    AddressRepo addressRepo;

    @RestClient
    private ProcessEngineClient processEngineClient;

    public List<EventEntity> findAll() {
        return eventRepo.findAll().stream().toList();
    }

    @Transactional
    public EventEntity save(EventEntity eventEntity, BufferedInputStream image) {
        byte[] imageAsBytes = null;
        if (image != null) {
            try {
                imageAsBytes = image.readAllBytes();
            } catch (Exception e) {
                throw new WebApplicationException("Error reading image", 500);
            }
        }
        AddressEntity addressEntity = eventEntity.getAddress();
        addressRepo.persist(addressEntity);
        eventEntity.setPicture(imageAsBytes);
        eventRepo.persist(eventEntity);
        // Start Process for Reminder on process engine
        //processEngineClient.startEventReminderProcess();
        return eventEntity;
    }

    public EventEntity findById(String eventId) {
        return eventRepo.findById(eventId);
    }

    public byte[] getPicture(String eventId) {
        return eventRepo.findById(eventId).getPicture();
    }

    @Transactional
    public EventEntity updateEntry(String eventId, EventEntity eventEntity) {
        EventEntity existingEvent = eventRepo.findById(eventId);
        if (existingEvent == null) {
            throw new WebApplicationException("No event found for id: " + eventId, 404);
        }
        existingEvent.setEntryStarted(eventEntity.isEntryStarted());
        return existingEvent;
    }

    public List<EventEntity> findByName(String name) {
        String likeName = "%" + name.toLowerCase() + "%";
        return eventRepo.find("LOWER(eventName) LIKE :name", Parameters.with("name", likeName)).list();
    }
}
