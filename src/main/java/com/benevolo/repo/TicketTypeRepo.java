package com.benevolo.repo;

import com.benevolo.entity.TicketTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TicketTypeRepo extends JpaRepository<TicketTypeEntity, String> {
    @Query("SELECT m FROM TicketTypeEntity AS m WHERE m.event.id = :eventId")
    List<TicketTypeEntity> findByEventId(String eventId);
}
