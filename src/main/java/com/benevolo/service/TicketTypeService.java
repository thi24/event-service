package com.benevolo.service;

import com.benevolo.entity.EventEntity;
import com.benevolo.entity.TicketTypeEntity;
import com.benevolo.repo.EventRepo;
import com.benevolo.repo.TicketTypeRepo;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.WebApplicationException;

import java.time.LocalDateTime;
import java.util.List;

@ApplicationScoped
public class TicketTypeService {

    @Inject
    TicketTypeRepo ticketTypeRepo;

    @Inject
    EventRepo eventRepo;

    public List<TicketTypeEntity> getByEventId(String eventId) {
        List<TicketTypeEntity> ticketTypeEntities = ticketTypeRepo.findByEventId(eventId);
        if (ticketTypeEntities.isEmpty())
            throw new WebApplicationException("No ticket types found for event with id: " + eventId, 404);
        return ticketTypeEntities;
    }

    public List<TicketTypeEntity> getValidByEventId(String eventId) {
        List<TicketTypeEntity> ticketTypeEntities = ticketTypeRepo.findByEventId(eventId);
        EventEntity event = eventRepo.findById(eventId);
        if (ticketTypeEntities.isEmpty()) {
            throw new WebApplicationException("No ticket types found for event with id: " + eventId, 404);
        }
        LocalDateTime dateNow = LocalDateTime.now();
        ticketTypeEntities.removeIf(ticketTypeEntity -> dateNow.isAfter(ticketTypeEntity.getValidTo()) || dateNow.isBefore(ticketTypeEntity.getValidFrom()) || !ticketTypeEntity.isActive() || ticketTypeEntity.isEntryStarted() || event.isEntryStarted());
        return ticketTypeEntities;
    }

    public TicketTypeEntity getById(String ticketTypeId) {
        TicketTypeEntity ticketTypeEntity = ticketTypeRepo.findById(ticketTypeId);
        if (ticketTypeEntity == null)
            throw new WebApplicationException("No ticket type found for id: " + ticketTypeId, 404);
        return ticketTypeRepo.findById(ticketTypeId);
    }

    @Transactional
    public void deleteById(String ticketTypeId) {
        ticketTypeRepo.deleteById(ticketTypeId);
    }

    @Transactional
    public TicketTypeEntity save(TicketTypeEntity ticketTypeEntity) {
        EventEntity event = eventRepo.findById(ticketTypeEntity.getEvent().getId());
        if (event == null) {
            throw new WebApplicationException("No event found for id: " + ticketTypeEntity.getEvent().getId(), 404);
        }
        ticketTypeEntity.setEvent(event);
        ticketTypeRepo.persist(ticketTypeEntity);
        return ticketTypeEntity;
    }

    @Transactional
    public void update(String ticketTypeId, TicketTypeEntity ticketTypeEntity) {
        TicketTypeEntity existingTicketType = ticketTypeRepo.findById(ticketTypeId);
        if (existingTicketType == null){
            throw new WebApplicationException("No ticket type found for id: " + ticketTypeId, 404);
        }
        existingTicketType.setActive(ticketTypeEntity.isActive());
        existingTicketType.setCapacity(ticketTypeEntity.getCapacity());
        existingTicketType.setPrice(ticketTypeEntity.getPrice());
        existingTicketType.setName(ticketTypeEntity.getName());
        existingTicketType.setTaxRate(ticketTypeEntity.getTaxRate());
        existingTicketType.setValidFrom(ticketTypeEntity.getValidFrom());
        existingTicketType.setValidTo(ticketTypeEntity.getValidTo());
    }

    @Transactional
    public TicketTypeEntity updateEntryStatus(String ticketTypeId, TicketTypeEntity ticketTypeEntity) {
        TicketTypeEntity existingTicketType = ticketTypeRepo.findById(ticketTypeId);
        if (existingTicketType == null) {
            throw new WebApplicationException("No ticket type found for id: " + ticketTypeId, 404);
        }
        existingTicketType.setEntryStarted(ticketTypeEntity.isEntryStarted());
        System.out.println("Entry started: " + existingTicketType.isEntryStarted());
        return existingTicketType;
    }

}
