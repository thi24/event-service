package com.benevolo;

import com.benevolo.dto.AddressDTO;
import com.benevolo.dto.EventDTO;
import com.benevolo.dto.TicketTypeDTO;
import com.benevolo.repo.EventRepo;
import com.benevolo.repo.TicketTypeRepo;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.h2.H2DatabaseTestResource;
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
@QuarkusTestResource(H2DatabaseTestResource.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TicketTypeRessourceTest {

    private final EventRepo eventRepo;
    private final TicketTypeRepo ticketTypeRepo;
    private String eventId = "";
    private String ticketTypeId = "";

    @Inject
    public TicketTypeRessourceTest(EventRepo eventRepo, TicketTypeRepo ticketTypeRepo) {
        this.eventRepo = eventRepo;
        this.ticketTypeRepo = ticketTypeRepo;
    }


    @Test
    @Order(1)
    @TestSecurity(user = "testUser", roles = {"admin", "user"})
    void createSampleEvent() {
        EventDTO eventDTO = new EventDTO("", "nameTest2",
                LocalDateTime.of(2024, 1, 12, 12, 0),
                LocalDateTime.of(2024, 1, 14, 12, 0),
                new AddressDTO("addressid", "street1", "Ingolstadt", "Deutschland", "85049"),
                "description");
        // set properties of eventDTO
        given().contentType(ContentType.MULTIPART)
                .multiPart("event", eventDTO, "application/json").
                when().
                post("/events").
                then().
                statusCode(204);
    }

    @Test
    @Order(2)
    @TestSecurity(user = "testUser", roles = {"admin", "user"})
        // Testing Save Method
    void createSampleTicketType() {
        eventId = eventRepo.findAll().stream().toList().get(0).getId();
        TicketTypeDTO ticketTypeDTO = new TicketTypeDTO("", "testEvent", 5000,
                19, 1000, true,
                LocalDateTime.of(2024, 1, 12, 12, 0),
                LocalDateTime.of(2024, 1, 14, 12, 0), eventId);

        given().contentType(ContentType.JSON).
                body(ticketTypeDTO).
                when().
                post("/ticket-types").
                then().
                statusCode(204);
    }


    @Test
    @Order(3)
    @TestSecurity(user = "testUser", roles = {"admin", "user"})
    void testGetByEventId() {
        eventId = eventRepo.findAll().stream().toList().get(0).getId();
        given().queryParam("eventId", eventId).when().
                get("/ticket-types").then().
                statusCode(200).
                body("$.size()", is(1)).
                body("[0].eventId", is(eventId));
    }

    @Test
    @Order(3)
    @TestSecurity(user = "testUser", roles = {"admin", "user"})
    void testGetById() {
        eventId = eventRepo.findAll().stream().toList().get(0).getId();
        ticketTypeId = ticketTypeRepo.findAll().stream().toList().get(0).getId();
        given().pathParam("ticketTypeId", ticketTypeId).
                when().
                get("/ticket-types/{ticketTypeId}").
                then().
                statusCode(200).
                body("id", is(ticketTypeId));
    }

    @Test
    @Order(4)
    @TestSecurity(user = "testUser", roles = {"admin", "user"})
    void testUpdate() {
        eventId = eventRepo.findAll().stream().toList().get(0).getId();
        ticketTypeId = ticketTypeRepo.findAll().stream().toList().get(0).getId();
        TicketTypeDTO ticketTypeDTO = new TicketTypeDTO(ticketTypeId, "testEventId", 5000,
                19, 2000, true,
                LocalDateTime.of(2024, 1, 12, 12, 0),
                LocalDateTime.of(2024, 1, 14, 12, 0), eventId);
        // check if current capacity is 1000
        given().pathParam("ticketTypeId", ticketTypeId).
                when().
                get("/ticket-types/{ticketTypeId}").
                then().
                statusCode(200).
                body("capacity", is(1000));
        // update capacity to 2000
        given().contentType(ContentType.JSON).body(ticketTypeDTO).
                pathParam("ticketTypeId", ticketTypeId).
                when().
                put("/ticket-types/{ticketTypeId}").
                then().statusCode(204);
        // check if capacity is updated
        given().pathParam("ticketTypeId", ticketTypeId).
                when().
                get("/ticket-types/{ticketTypeId}").
                then().
                statusCode(200).
                body("capacity", is(2000));
    }

    @Test
    @Order(5)
    @TestSecurity(user = "testUser", roles = {"admin", "user"})
    void testDeleteById() {
        eventId = eventRepo.findAll().stream().toList().get(0).getId();
        ticketTypeId = ticketTypeRepo.findAll().stream().toList().get(0).getId();
        given().pathParam("ticketTypeId", ticketTypeId).
                when().
                delete("/ticket-types/{ticketTypeId}").
                then().
                statusCode(204);
        // check if ticket type is deleted
        given().pathParam("ticketTypeId", ticketTypeId).
                when().
                get("/ticket-types/{ticketTypeId}").
                then().
                statusCode(404);
    }
}


