package com.benevolo.service;

import com.benevolo.dto.TicketTypeDTO;
import com.benevolo.entity.TicketTypeEntity;
import com.benevolo.mapper.TicketTypeMapper;
import com.benevolo.repo.EventRepo;
import com.benevolo.repo.TicketTypeRepo;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.WebApplicationException;

import java.util.List;
import java.util.Optional;
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
        System.out.println("TicketTypeService.getByEventId");
        System.out.println("eventId = [" + eventId + "]");
        List<TicketTypeEntity> ticketTypeEntities = ticketTypeRepo.findByEventId(eventId);
        //System.out.println("ticketTypeEntities = " + ticketTypeEntities.get(0).getName());
        return TicketTypeMapper.mapToDTO(ticketTypeEntities);
    }
    public TicketTypeDTO getById(String ticketTypeId) {
        Optional<TicketTypeEntity> ticketTypeEntityOptional = ticketTypeRepo.findById(ticketTypeId);
        if(ticketTypeEntityOptional.isEmpty()) {
            throw new WebApplicationException("TicketType not found", 404);
        }
        return TicketTypeMapper.map(ticketTypeEntityOptional.get());
    }

    public void deleteById(String ticketTypeId) {
        ticketTypeRepo.deleteById(ticketTypeId);
    }
    public void save(TicketTypeDTO ticketTypeDTO) {
        TicketTypeEntity ticketTypeEntity = TicketTypeMapper.map(ticketTypeDTO);
        if(ticketTypeDTO.eventId() == null || ticketTypeDTO.eventId().isBlank()) {
            ticketTypeEntity.setId(UUID.randomUUID().toString());
        }
        ticketTypeEntity.setEvent(eventRepo.findById(ticketTypeDTO.eventId()).orElseThrow());
        ticketTypeRepo.save(ticketTypeEntity);
    }

    public void update(String ticketTypeId, TicketTypeDTO ticketTypeDTO) {
        TicketTypeEntity ticketTypeEntity = ticketTypeRepo.findById(ticketTypeId).orElseThrow();
        ticketTypeEntity.setName(ticketTypeDTO.name());
        ticketTypeEntity.setActive(ticketTypeDTO.active());
        ticketTypeEntity.setCapacity(ticketTypeDTO.capacity());
        ticketTypeEntity.setTaxRate(ticketTypeDTO.taxRate());
        ticketTypeEntity.setValidFrom(ticketTypeDTO.validFrom());
        ticketTypeEntity.setValidTo(ticketTypeDTO.validTo());
        ticketTypeEntity.setPrice(ticketTypeDTO.price());
        ticketTypeRepo.save(ticketTypeEntity);
    }


}
