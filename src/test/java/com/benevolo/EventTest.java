package com.benevolo;

import com.benevolo.dto.EventDTO;
import com.benevolo.entity.EventEntity;
import com.benevolo.repo.EventRepo;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.quarkus.test.TestTransaction;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.h2.H2DatabaseTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.MediaType;
import jdk.jfr.Event;
import org.jboss.resteasy.reactive.server.spi.RuntimeConfiguration;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.time.LocalDateTime;
import java.util.List;

@QuarkusTest
@QuarkusTestResource(H2DatabaseTestResource.class)
public class EventTest {

    private final EventRepo eventRepo;

    @Inject
    public EventTest(EventRepo eventRepo) {
        this.eventRepo = eventRepo;
    }

    @Test
    @Order(1)
    public void testCreateEvent() {
        EventDTO eventDTO = new EventDTO("Testevent", LocalDateTime.of(2022, 5, 6, 10, 0), LocalDateTime.of(2022, 5, 7, 18, 0));
        RestAssured.given().body(eventDTO).contentType(MediaType.APPLICATION_JSON).when().post("/events");
        Assertions.assertEquals(1, eventRepo.findAll().size());
    }

    @Test
    @Order(2)
    public void testGetAllEvents() throws Exception {
        String body = RestAssured.given().get("/events").getBody().asString();
        List<EventDTO> events = new ObjectMapper().registerModule(new JavaTimeModule()).readValue(body, new TypeReference<>() {});
        Assertions.assertEquals(1, events.size());
    }

}
