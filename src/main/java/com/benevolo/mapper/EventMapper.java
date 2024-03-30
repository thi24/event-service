package com.benevolo.mapper;

import com.benevolo.dto.EventDTO;
import com.benevolo.entity.EventEntity;

import java.util.List;

public class EventMapper {

    public static List<EventDTO> map(List<EventEntity> events) {
        return events.stream().map(EventMapper::map).toList();
    }

    public static EventDTO map(EventEntity event) {
        return new EventDTO(event.getName(), event.getStartsAt(), event.getEndsAt());
    }

}
