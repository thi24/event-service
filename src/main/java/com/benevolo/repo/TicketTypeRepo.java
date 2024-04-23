package com.benevolo.repo;

import com.benevolo.entity.TicketTypeEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class TicketTypeRepo implements PanacheRepositoryBase<TicketTypeEntity, String> {
    public List<TicketTypeEntity> findByEventId(String eventId) {
        return list("SELECT m FROM TicketTypeEntity AS m WHERE m.event.id = ?1", eventId);
    }
}
