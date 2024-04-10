package com.benevolo.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "ticket_type")
public class TicketTypeEntity {
    @Id
    private String id;
    private String name;
    private double price;
    @Column(name = "tax_rate")
    private double taxRate;
    private int capacity;
    private boolean active;
    @Column(name = "valid_from")
    private LocalDateTime validFrom;
    @Column(name = "valid_to")
    private LocalDateTime validTo;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id")
    private EventEntity event;

    public TicketTypeEntity() {
    }

    public TicketTypeEntity(String id, String name, double price, double taxRate, int capacity, boolean active, LocalDateTime validFrom, LocalDateTime validTo) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.taxRate = taxRate;
        this.capacity = capacity;
        this.active = active;
        this.validFrom = validFrom;
        this.validTo = validTo;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(double taxRate) {
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
}
