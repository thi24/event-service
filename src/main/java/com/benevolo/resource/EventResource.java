package com.benevolo.resource;

import com.benevolo.dto.EventDTO;
import com.benevolo.entity.EventEntity;
import com.benevolo.repo.EventRepo;
import com.benevolo.service.EventService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Application;
import jakarta.ws.rs.core.MediaType;

import java.util.List;
import java.util.Optional;

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
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces
    public void post(EventDTO eventDTO) {
        eventService.save(eventDTO);
    }

}
