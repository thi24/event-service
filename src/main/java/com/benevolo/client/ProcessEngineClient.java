package com.benevolo.client;

import io.quarkus.rest.client.reactive.ClientQueryParam;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.annotation.RegisterClientHeaders;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterClientHeaders
@RegisterRestClient(configKey = "process-engine")
public interface ProcessEngineClient {

    @POST
    @Path("/atlas_engine/api/v1/messages/Reminder/trigger")
    @ClientQueryParam(name = "processInstanceId", value = "TicketReminder_Process")
    @Consumes(MediaType.APPLICATION_JSON)
    void startEventReminderProcess();
}
