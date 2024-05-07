package com.benevolo;

import com.benevolo.entity.AddressEntity;
import com.benevolo.entity.EventEntity;
import com.benevolo.repo.EventRepo;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import io.restassured.http.ContentType;
import jakarta.inject.Inject;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.time.LocalDateTime;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class EventResourceTest {

    private final EventRepo eventRepo;
    private String eventId = "";

    @Inject
    public EventResourceTest(EventRepo eventRepo) {
        this.eventRepo = eventRepo;
    }

    @Test
    @Order(1)
    @TestSecurity(user = "testUser", roles = {"admin", "user"})
    void testCreateEvent() {
        EventEntity eventEntity = new EventEntity("TestEvent", LocalDateTime.of(2022, 5, 6, 10, 0), LocalDateTime.of(2022, 5, 7, 18, 0), new AddressEntity("addressid", "street1", "Ingolstadt", "Deutschland", null), "description");
        given().contentType(ContentType.MULTIPART).multiPart("event", eventEntity, "application/json").when().post("/events").then().statusCode(200);
    }

    @Test
    @Order(2)
    @TestSecurity(user = "testUser", roles = {"admin", "user"})
    void testGetAllEvents() {
        given().get("/events").then().statusCode(200).body("size()", is(2));
    }

    @Test
    @Order(3)
    @TestSecurity(user = "testUser", roles = {"admin", "user"})
    void testGetEventById() {
        eventId = eventRepo.findAll().stream().toList().get(0).getId();
        given().pathParam("eventId", eventId).when().get("/events/{eventId}").then().statusCode(200).body("id", is(eventId));
    }
}