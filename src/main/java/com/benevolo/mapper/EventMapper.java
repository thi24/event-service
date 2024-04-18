package com.benevolo.mapper;

import com.benevolo.dto.EventDTO;
import com.benevolo.entity.EventEntity;

import java.util.List;

public class EventMapper {
    private EventMapper() {
        // private constructor
    }

    public static List<EventDTO> map(List<EventEntity> events) {
        return events.stream().map(EventMapper::map).toList();
    }

    public static EventDTO map(EventEntity event) {
        return new EventDTO(
                event.getId(),
                event.getEventName(),
                event.getStartsAt(),
                event.getEndsAt(),
                AddressMapper.map(event.getAddress()),
                event.getDescription()
        );
    }

    public static EventEntity map(EventDTO eventDTO) {
        return new EventEntity(
                eventDTO.id(),
                eventDTO.eventName(),
                eventDTO.startsAt(),
                eventDTO.endsAt(),
                AddressMapper.map(eventDTO.address()),
                eventDTO.description()
        );
    }

}
