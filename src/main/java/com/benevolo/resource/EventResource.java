package com.benevolo.resource;

import com.benevolo.dto.EventDTO;
import com.benevolo.service.EventService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.io.InputStream;
import java.util.List;

@Path("/events")
public class EventResource {
    private final EventService eventService;

    @Inject
    public EventResource(EventService eventService) {
        this.eventService = eventService;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<EventDTO> getAll() {
        return eventService.findAll();
    }

    @GET
    @Path("/{eventId}")
    @Produces(MediaType.APPLICATION_JSON)
    public EventDTO getById(@PathParam("eventId") String eventId) {
        return eventService.findById(eventId);
    }

    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces
    public void post(
            @FormParam("eventName") String eventName,
            @FormParam("startsAt") String startsAt,
            @FormParam("endsAt") String endsAt,
            @FormParam("street") String street,
            @FormParam("zip") String zip,
            @FormParam("city") String city,
            @FormParam("state") String state,
            @FormParam("picture") InputStream picture, //Ändern zu InputStream
            @FormParam("description") String description,
    ) {
        eventService.save(eventDTO);
    }
}
