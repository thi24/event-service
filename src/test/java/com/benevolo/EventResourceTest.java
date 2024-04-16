package com.benevolo;

import com.benevolo.dto.AddressDTO;
import com.benevolo.dto.EventDTO;
import com.benevolo.repo.EventRepo;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.quarkus.security.identity.SecurityIdentity;
import io.quarkus.test.InjectMock;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.h2.H2DatabaseTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.MediaType;
import org.junit.jupiter.api.*;

import java.time.LocalDateTime;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
@QuarkusTestResource(H2DatabaseTestResource.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class EventResourceTest {

    private final EventRepo eventRepo;
    private static final String EVENT_ID = "eventid";

    @Inject
    public EventResourceTest(EventRepo eventRepo) {
        this.eventRepo = eventRepo;
    }

    @Test
    @Order(1)
    @TestSecurity(user = "testUser", roles = {"admin", "user"})
    public void testCreateEvent() {
        EventDTO eventDTO = new EventDTO(EVENT_ID,
                "TestEvent",
                LocalDateTime.of(2022, 5, 6, 10, 0),
                LocalDateTime.of(2022, 5, 7, 18, 0),
                new AddressDTO("addressid", "street1", "Ingolstadt", "Deutschland", "85049"),
                "description");
        //RestAssured.given().body(eventDTO).contentType(MediaType.APPLICATION_JSON).when().post("/events");
        given().contentType(ContentType.JSON).
                body(eventDTO).
                when().
                post("/events").
                then().
                statusCode(204);
    }

    @Test
    @Order(2)
    @TestSecurity(user = "testUser", roles = {"admin", "user"})
    public void testGetAllEvents() throws Exception {
        String body = RestAssured.given().get("/events").getBody().asString();
        System.out.println(eventRepo.findAll().size());
        List<EventDTO> events = new ObjectMapper().registerModule(new JavaTimeModule()).readValue(body, new TypeReference<>() {
        });
        Assertions.assertEquals(1, events.size());
    }

    @Test
    @Order(3)
    @TestSecurity(user = "testUser", roles = {"admin", "user"})
    public void testGetEventById() throws Exception {
        given().pathParam("eventId", EVENT_ID).
                when().
                get("/events/{eventId}").
                then().
                statusCode(200).
                body("id", is(EVENT_ID));
    }

}