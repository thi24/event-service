package com.benevolo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "event")
public class EventEntity {

    @Id
    private String id;
    @Column(name = "event_name")
    private String name;
    @Column(name = "starts_at")
    private LocalDateTime startsAt;
    @Column(name = "ends_at")
    private LocalDateTime endsAt;

    public EventEntity() {
    }

    public EventEntity(String name, LocalDateTime startsAt, LocalDateTime endsAt) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.startsAt = startsAt;
        this.endsAt = endsAt;
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
}
