package com.benevolo;

import com.benevolo.dto.AddressDTO;
import com.benevolo.dto.EventDTO;
import com.benevolo.dto.TicketTypeDTO;
import com.benevolo.repo.EventRepo;
import com.benevolo.repo.TicketTypeRepo;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.h2.H2DatabaseTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import jakarta.inject.Inject;
import org.junit.jupiter.api.*;

import java.time.LocalDateTime;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
@QuarkusTestResource(H2DatabaseTestResource.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TicketTypeRessourceTest {

    private static final String EVENT_ID = "eventid";
    private static final String TICKET_TYPE_ID = "tickettypeid";

    private final EventRepo eventRepo;
    private final TicketTypeRepo ticketTypeRepo;

    @Inject
    public TicketTypeRessourceTest(EventRepo eventRepo, TicketTypeRepo ticketTypeRepo) {
        this.eventRepo = eventRepo;
        this.ticketTypeRepo = ticketTypeRepo;
    }


    @Test
    @Order(1)
    public void createSampleEvent() {
        EventDTO eventDTO = new EventDTO(EVENT_ID, "nameTest2",
                LocalDateTime.of(2024, 1, 12, 12, 0),
                LocalDateTime.of(2024, 1, 14, 12, 0),
                new AddressDTO("addressid", "street1", "Ingolstadt", "Deutschland", "85049"),
                "description");
        // set properties of eventDTO
        given().contentType(ContentType.JSON).
                body(eventDTO).
                when().
                post("/events").
                then().
                statusCode(204);
    }

    @Test
    @Order(2)
    // Testing Save Method
    public void createSampleTicketType() {
        TicketTypeDTO ticketTypeDTO = new TicketTypeDTO(TICKET_TYPE_ID, "testEvent", 50.00,
                0.19, 1000, true,
                LocalDateTime.of(2024, 1, 12, 12, 0),
                LocalDateTime.of(2024, 1, 14, 12, 0), EVENT_ID);

        given().contentType(ContentType.JSON).
                body(ticketTypeDTO).
                when().
                post("/ticket-types").
                then().
                statusCode(204);
        System.out.println(ticketTypeRepo.findAll().size());
    }


    @Test
    @Order(3)
    public void testGetByEventId() {
        given().queryParam("eventId", EVENT_ID).when().
                get("/ticket-types").then().
                statusCode(200).
                body("$.size()", is(1)).
                body("[0].eventId", is(EVENT_ID));
    }
    @Test
    @Order(3)
    public void testGetById() {
        given().pathParam("ticketTypeId", TICKET_TYPE_ID).
                when().
                get("/ticket-types/{ticketTypeId}").
                then().
                statusCode(200).
                body("id", is(TICKET_TYPE_ID));
    }
    @Test
    @Order(4)
    public void testUpdate() {
        TicketTypeDTO ticketTypeDTO = new TicketTypeDTO(TICKET_TYPE_ID, "testEventId", 50.00,
                0.19, 2000, true,
                LocalDateTime.of(2024, 1, 12, 12, 0),
                LocalDateTime.of(2024, 1, 14, 12, 0), EVENT_ID);
        // check if current capacity is 1000
        given().pathParam("ticketTypeId", TICKET_TYPE_ID).
                when().
                get("/ticket-types/{ticketTypeId}").
                then().
                statusCode(200).
                body("capacity", is(1000));
        // update capacity to 2000
        given().contentType(ContentType.JSON).body(ticketTypeDTO).
                pathParam("ticketTypeId", TICKET_TYPE_ID).
                when().
                put("/ticket-types/{ticketTypeId}").
                then().statusCode(204);
        // check if capacity is updated
        given().pathParam("ticketTypeId", TICKET_TYPE_ID).
                when().
                get("/ticket-types/{ticketTypeId}").
                then().
                statusCode(200).
                body("capacity", is(2000));
    }
    @Test
    @Order(5)
    public void testDeleteById() {
        given().pathParam("ticketTypeId", TICKET_TYPE_ID).
                when().
                delete("/ticket-types/{ticketTypeId}").
                then().
                statusCode(204);
        // check if ticket type is deleted
        given().pathParam("ticketTypeId", TICKET_TYPE_ID).
                when().
                get("/ticket-types/{ticketTypeId}").
                then().
                statusCode(404);
    }
}


