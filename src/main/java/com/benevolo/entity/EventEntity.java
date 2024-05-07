package com.benevolo.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "event")
public class EventEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "event_name")
    private String eventName;

    @Column(name = "starts_at")
    private LocalDateTime startsAt;

    @Column(name = "ends_at")
    private LocalDateTime endsAt;

    @OneToOne()
    @JoinColumn(name = "address_id")
    @JsonManagedReference
    private AddressEntity address;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "event")
    @JsonBackReference
    private List<TicketTypeEntity> ticketTypes;

    @Basic(fetch = FetchType.LAZY)
    @Column(name = "picture")
    @JsonIgnore
    private byte[] picture;

    public EventEntity() {
    }

    public EventEntity(String eventId) {
        this.id = eventId;
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

    public byte[] getPicture() {
        return picture;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }
}
