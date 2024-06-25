package com.benevolo.client;

import com.benevolo.entity.EventEntity;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.annotation.RegisterClientHeaders;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterClientHeaders
@RegisterRestClient(configKey = "process-engine")
public interface ProcessEngineClient {

    @POST
    @Path("/v1.0/messages/Eventanlegen")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    void startEventReminderProcess(EventEntity eventEntity);
}
