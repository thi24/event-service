package com.benevolo.mocks;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.github.tomakehurst.wiremock.WireMockServer;
import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;

import java.util.Map;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;

public class ProcessEngineMock implements QuarkusTestResourceLifecycleManager {

    private WireMockServer wireMockServer;

    @Override
    public Map<String, String> start() {
        wireMockServer = new WireMockServer(options().port(8082));
        wireMockServer.start();

        try {
            wireMockServer.stubFor(post(urlMatching("/atlas_engine/api/v1/messages/Reminder/.*"))
                    .willReturn(aResponse()
                            .withHeader("Content-Type", "application/json")
                            .withBody(
                                    new ObjectMapper().registerModule(new JavaTimeModule()).writeValueAsString(""
                                    ))));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        
        return Map.of("quarkus.rest-client.process-engine.url", wireMockServer.baseUrl());
    }

    @Override
    public void stop() {
        if (null != wireMockServer) {
            wireMockServer.stop();
        }
    }
}
