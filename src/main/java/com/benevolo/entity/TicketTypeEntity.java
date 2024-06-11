package com.benevolo.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "ticket_type")
public class TicketTypeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "ticket_type_name")
    private String name;

    private int price;
    @Column(name = "tax_rate")
    private int taxRate;

    private int capacity;
    private boolean active;
    @Column(name = "valid_from")
    private LocalDateTime validFrom;

    @Column(name = "valid_to")
    private LocalDateTime validTo;

    @Column(name = "entry_started")
    private boolean entryStarted;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "event_id")
    private EventEntity event;

    public TicketTypeEntity() {
    }

    public TicketTypeEntity(String id, String name, int price, int taxRate, int capacity, boolean active, LocalDateTime validFrom, LocalDateTime validTo, String eventId) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.taxRate = taxRate;
        this.capacity = capacity;
        this.active = active;
        this.validFrom = validFrom;
        this.validTo = validTo;
        this.event = new EventEntity(eventId);
    }

    public TicketTypeEntity(String name, int price, int taxRate, int capacity, boolean active, LocalDateTime validFrom, LocalDateTime validTo, String eventId) {
        this.name = name;
        this.price = price;
        this.taxRate = taxRate;
        this.capacity = capacity;
        this.active = active;
        this.validFrom = validFrom;
        this.validTo = validTo;
        this.event = new EventEntity(eventId);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(int taxRate) {
        this.taxRate = taxRate;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public LocalDateTime getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(LocalDateTime validFrom) {
        this.validFrom = validFrom;
    }

    public LocalDateTime getValidTo() {
        return validTo;
    }

    public void setValidTo(LocalDateTime validTo) {
        this.validTo = validTo;
    }

    public EventEntity getEvent() {
        return event;
    }

    public void setEvent(EventEntity event) {
        this.event = event;
    }

    public boolean isEntryStarted() {
        return entryStarted;
    }

    public void setEntryStarted(boolean entryStarted) {
        this.entryStarted = entryStarted;
    }
}
