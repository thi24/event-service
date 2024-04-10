package com.benevolo.mapper;

import com.benevolo.dto.EventDTO;
import com.benevolo.dto.TicketTypeDTO;
import com.benevolo.entity.EventEntity;
import com.benevolo.entity.TicketTypeEntity;
import jakarta.inject.Inject;

import java.util.List;

public class TicketTypeMapper {
    @Inject
    public TicketTypeMapper() {
    }

    public static List<TicketTypeDTO> mapToDTO(List<TicketTypeEntity> ticketTypes) {
        return ticketTypes.stream().map(TicketTypeMapper::map).toList();
    }
    public static TicketTypeDTO map(TicketTypeEntity entity) {
        return new TicketTypeDTO(entity.getId(), entity.getName(), entity.getPrice(), entity.getTaxRate(),
                entity.getCapacity(), entity.isActive(), entity.getValidFrom(), entity.getValidTo(), entity.getEvent().getId());
    }

    public static List<TicketTypeEntity> mapToEntity(List<TicketTypeDTO> ticketTypeDTOS) {
        return ticketTypeDTOS.stream().map(TicketTypeMapper::map).toList();
    }

    public static TicketTypeEntity map(TicketTypeDTO ticketTypeDTO) {
        return new TicketTypeEntity(ticketTypeDTO.id(), ticketTypeDTO.name(), ticketTypeDTO.price(),
                ticketTypeDTO.taxRate(), ticketTypeDTO.capacity(), ticketTypeDTO.active(), ticketTypeDTO.validFrom(),
                ticketTypeDTO.validTo());
    }
}
