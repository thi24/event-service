package com.benevolo.dto;

import java.time.LocalDateTime;

public record EventDTO(String name, LocalDateTime startsAt, LocalDateTime endsAt) {
}
