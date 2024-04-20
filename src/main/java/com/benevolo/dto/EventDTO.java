package com.benevolo.dto;

import java.time.LocalDateTime;

public record EventDTO(String id, String eventName, LocalDateTime startsAt, LocalDateTime endsAt, AddressDTO address,
                       String description, Byte[] picture) {
}



