package com.benevolo;

import com.benevolo.entity.AddressEntity;
import com.benevolo.entity.EventEntity;
import com.benevolo.entity.TicketTypeEntity;
import com.benevolo.repo.EventRepo;
import com.benevolo.repo.TicketTypeRepo;
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
        EventEntity eventEntity = new EventEntity("nameTest2",
                LocalDateTime.of(2024, 1, 12, 12, 0),
                LocalDateTime.of(2024, 1, 14, 12, 0),
                new AddressEntity("addressid", "street1", "Ingolstadt", "Deutschland", null),
                "description");
        // set properties of eventDTO
        given().contentType(ContentType.MULTIPART)
                .multiPart("event", eventEntity, "application/json").
                when().
                post("/events").
                then().
                statusCode(200);
    }

    @Test
    @Order(2)
    @TestSecurity(user = "testUser", roles = {"admin", "user"})
        // Testing Save Method
    void createSampleTicketType() {
        eventId = eventRepo.findAll().stream().toList().get(0).getId();
        System.out.println(eventId);
        TicketTypeEntity ticketTypeEntity = new TicketTypeEntity("testEvent", 5000,
                19, 1000, true,
                LocalDateTime.of(2024, 1, 12, 12, 0),
                LocalDateTime.of(2024, 1, 14, 12, 0), eventId);

        given().contentType(ContentType.JSON).
                body(ticketTypeEntity).
                when().
                post("/ticket-types").
                then().
                statusCode(200);
        System.out.println(ticketTypeRepo.findAll().stream().toList());
    }


    @Test
    @Order(3)
    @TestSecurity(user = "testUser", roles = {"admin", "user"})
    void testGetByEventId() {
        eventId = eventRepo.findAll().stream().toList().get(0).getId();
        /*
        String responseBody = given().queryParam("eventId", eventId).when().
                get("/ticket-types").then().
                statusCode(200).
                extract().body().asString();
        System.out.println(responseBody);
        */
        given().queryParam("eventId", eventId).when().
                get("/ticket-types").then().
                statusCode(200).
                body("$.size()", is(1)).
                body("[0].event.id", is(eventId));

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
        TicketTypeEntity ticketTypeEntity = new TicketTypeEntity(ticketTypeId, "testEventId", 5000,
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
        given().contentType(ContentType.JSON).body(ticketTypeEntity).
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


