package com.benevolo.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "event")
public class EventEntity {

    @Id
    private String id;
    @Column(name = "event_name")
    private String eventName;
    @Column(name = "starts_at")
    private LocalDateTime startsAt;
    @Column(name = "ends_at")
    private LocalDateTime endsAt;
    @OneToOne()
    @JoinColumn(name = "address_id")
    private AddressEntity address;
    @Column(name = "description")
    private String description;
    @OneToMany(mappedBy = "event")
    private List<TicketTypeEntity> ticketTypes;

    public EventEntity() {
    }

    public EventEntity(String eventName, LocalDateTime startsAt, LocalDateTime endsAt, AddressEntity address, String description) {
        this.eventName = eventName;
        this.startsAt = startsAt;
        this.endsAt = endsAt;
        this.address = address;
        this.description = description;
    }

    public EventEntity(String id, String eventName, LocalDateTime startsAt, LocalDateTime endsAt, AddressEntity address, String description) {
        this.id = id;
        this.eventName = eventName;
        this.startsAt = startsAt;
        this.endsAt = endsAt;
        this.address = address;
        this.description = description;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public LocalDateTime getStartsAt() {
        return startsAt;
    }

    public void setStartsAt(LocalDateTime startsAt) {
        this.startsAt = startsAt;
    }

    public LocalDateTime getEndsAt() {
        return endsAt;
    }

    public void setEndsAt(LocalDateTime endsAt) {
        this.endsAt = endsAt;
    }

    public AddressEntity getAddress() {
        return address;
    }

    public void setAddress(AddressEntity address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<TicketTypeEntity> getTicketTypes() {
        return ticketTypes;
    }

    public void setTicketTypes(List<TicketTypeEntity> ticketTypes) {
        this.ticketTypes = ticketTypes;
    }
}
