package com.benevolo.resource;

import com.benevolo.dto.TicketTypeDTO;
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
    public List<TicketTypeDTO> getByEventId(@RestQuery("eventId") String eventId) {
        return ticketTypeService.getByEventId(eventId);
    }

    @GET
    @Path("/{ticketTypeId}")
    public TicketTypeDTO getById(@PathParam("ticketTypeId") String ticketTypeId) {
        return ticketTypeService.getById(ticketTypeId);
    }

    @DELETE
    @Path("/{ticketTypeId}")
    public void deleteById(@PathParam("ticketTypeId") String ticketTypeId) {
        ticketTypeService.deleteById(ticketTypeId);
    }

    @POST
    public void save(TicketTypeDTO ticketTypeDTO) {
        ticketTypeService.save(ticketTypeDTO);
    }

    @PUT
    @Path("/{ticketTypeId}")
    public void update(@RestPath String ticketTypeId, TicketTypeDTO ticketTypeDTO) {
        ticketTypeService.update(ticketTypeId, ticketTypeDTO);
    }
}
