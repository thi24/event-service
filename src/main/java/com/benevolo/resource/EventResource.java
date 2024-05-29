package com.benevolo.resource;

import com.benevolo.entity.EventEntity;
import com.benevolo.service.EventService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.jboss.resteasy.reactive.PartType;
import org.jboss.resteasy.reactive.RestForm;

import java.io.BufferedInputStream;
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
    public List<EventEntity> getAll() {
        return eventService.findAll();
    }

    @GET
    @Path("/{eventId}")
    @Produces(MediaType.APPLICATION_JSON)
    public EventEntity getById(@PathParam("eventId") String eventId) {
        return eventService.findById(eventId);
    }

    @GET
    @Path("/public")
    @Produces(MediaType.APPLICATION_JSON)
    public List<EventEntity> getAllPublic() {
        return eventService.findAll();
    }

    @GET
    @Path("/public/{eventId}")
    @Produces(MediaType.APPLICATION_JSON)
    public EventEntity getByIdPublic(@PathParam("eventId") String eventId) {
        return eventService.findById(eventId);
    }

    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public EventEntity post(
            @RestForm("event") @PartType(MediaType.APPLICATION_JSON) EventEntity eventEntity,
            @RestForm("image") @PartType(MediaType.APPLICATION_OCTET_STREAM) BufferedInputStream image) {
        return eventService.save(eventEntity, image);
    }

    @GET
    @Path("/{eventId}/image")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public byte[] getImage(@PathParam("eventId") String eventId) {
        return eventService.getPicture(eventId);
    }

    @GET
    @Path("/public/{eventId}/image")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public byte[] getImagePublic(@PathParam("eventId") String eventId) {
        return eventService.getPicture(eventId);
    }
}
