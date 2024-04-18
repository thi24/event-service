package com.benevolo.service;

import com.benevolo.dto.TicketTypeDTO;
import com.benevolo.entity.TicketTypeEntity;
import com.benevolo.mapper.TicketTypeMapper;
import com.benevolo.repo.EventRepo;
import com.benevolo.repo.TicketTypeRepo;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.WebApplicationException;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class TicketTypeService {
    private final TicketTypeRepo ticketTypeRepo;
    private final EventRepo eventRepo;

    @Inject
    public TicketTypeService(TicketTypeRepo ticketTypeRepo, EventRepo eventRepo) {
        this.ticketTypeRepo = ticketTypeRepo;
        this.eventRepo = eventRepo;
    }

    public List<TicketTypeDTO> getByEventId(String eventId) {
        List<TicketTypeEntity> ticketTypeEntities = ticketTypeRepo.findByEventId(eventId);
        if (ticketTypeEntities.isEmpty())
            throw new WebApplicationException("No ticket types found for event with id: " + eventId, 404);
        return TicketTypeMapper.mapToDTO(ticketTypeEntities);
    }

    public TicketTypeDTO getById(String ticketTypeId) {
        TicketTypeEntity ticketTypeEntity = ticketTypeRepo.findById(ticketTypeId);
        if (ticketTypeEntity == null)
            throw new WebApplicationException("No ticket type found for id: " + ticketTypeId, 404);
        return TicketTypeMapper.map(ticketTypeRepo.findById(ticketTypeId));
    }

    @Transactional
    public void deleteById(String ticketTypeId) {
        ticketTypeRepo.deleteById(ticketTypeId);
    }

    @Transactional
    public void save(TicketTypeDTO ticketTypeDTO) {
        TicketTypeEntity ticketTypeEntity = TicketTypeMapper.map(ticketTypeDTO);
        ticketTypeEntity.setId(UUID.randomUUID().toString());
        ticketTypeEntity.setEvent(eventRepo.findById(ticketTypeDTO.eventId()));
        ticketTypeRepo.persist(ticketTypeEntity);
    }

    @Transactional
    public void update(String ticketTypeId, TicketTypeDTO ticketTypeDTO) {
        TicketTypeEntity ticketTypeEntity = ticketTypeRepo.findById(ticketTypeId);
        ticketTypeEntity.setName(ticketTypeDTO.name());
        ticketTypeEntity.setActive(ticketTypeDTO.active());
        ticketTypeEntity.setCapacity(ticketTypeDTO.capacity());
        ticketTypeEntity.setTaxRate(ticketTypeDTO.taxRate());
        ticketTypeEntity.setValidFrom(ticketTypeDTO.validFrom());
        ticketTypeEntity.setValidTo(ticketTypeDTO.validTo());
        ticketTypeEntity.setPrice(ticketTypeDTO.price());
        ticketTypeRepo.persist(ticketTypeEntity);
    }


}
