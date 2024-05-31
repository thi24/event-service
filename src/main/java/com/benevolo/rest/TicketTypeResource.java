package com.benevolo.rest;

import com.benevolo.entity.TicketTypeEntity;
import com.benevolo.service.TicketTypeService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import org.jboss.resteasy.reactive.RestPath;
import org.jboss.resteasy.reactive.RestQuery;

import java.util.List;

@Path("/ticket-types")
public class TicketTypeResource {
    private final TicketTypeService ticketTypeService;

    @Inject
    public TicketTypeResource(TicketTypeService ticketTypeService) {
        this.ticketTypeService = ticketTypeService;
    }

    @GET
    public List<TicketTypeEntity> getByEventId(@RestQuery("eventId") String eventId) {
        return ticketTypeService.getByEventId(eventId);
    }

    @GET
    @Path("/public")
    public List<TicketTypeEntity> getByEventIdPublic(@RestQuery("eventId") String eventId) {
        return ticketTypeService.getByEventId(eventId);
    }

    @GET
    @Path("/public/valid")
    public List<TicketTypeEntity> getValidByEventIdPublic(@RestQuery("eventId") String eventId) {
        return ticketTypeService.getValidByEventId(eventId);
    }

    @GET
    @Path("/{ticketTypeId}")
    public TicketTypeEntity getById(@PathParam("ticketTypeId") String ticketTypeId) {
        return ticketTypeService.getById(ticketTypeId);
    }

    @DELETE
    @Path("/{ticketTypeId}")
    public void deleteById(@PathParam("ticketTypeId") String ticketTypeId) {
        ticketTypeService.deleteById(ticketTypeId);
    }

    @POST
    public TicketTypeEntity save(TicketTypeEntity ticketTypeEntity) {
        return ticketTypeService.save(ticketTypeEntity);

    }

    @PUT
    @Path("/{ticketTypeId}")
    public void update(@PathParam("ticketTypeId") String ticketTypeId, TicketTypeEntity ticketTypeEntity) {
        ticketTypeService.update(ticketTypeId, ticketTypeEntity);
    }
}
