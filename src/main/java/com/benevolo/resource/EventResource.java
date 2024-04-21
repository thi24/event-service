package com.benevolo.resource;

import com.benevolo.dto.EventDTO;
import com.benevolo.service.EventService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.jboss.resteasy.reactive.PartType;
import org.jboss.resteasy.reactive.RestForm;
import org.jboss.resteasy.reactive.multipart.FileUpload;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
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
    public void post(
            @RestForm("event") @PartType(MediaType.APPLICATION_JSON) EventDTO eventDTO,
            @RestForm("image") @PartType(MediaType.APPLICATION_OCTET_STREAM) BufferedInputStream image) throws IOException {

        eventService.save(eventDTO, image.readAllBytes());
    }
}
