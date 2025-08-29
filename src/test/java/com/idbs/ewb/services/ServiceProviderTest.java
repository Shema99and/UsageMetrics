package com.idbs.ewb.services;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;

class ServiceProviderTest {

    @Test
    void testWebClientIsConstructed() {
        // Mock configuration values
        int maxConnections = 100;
        int durationInSeconds = 30;
        int durationInMin = 5;
        int durationInMillis = 5000;
        String scheme = "http";
        String host = "localhost";
        int port = 8080;
        String servicebase = "/api";

        // Instantiate ServiceProvider
        ServiceProvider serviceProvider = new ServiceProvider(
            maxConnections,
            durationInSeconds,
            durationInMin,
            durationInMillis,
            scheme,
            host,
            port,
            servicebase
        );

        // Validate WebClient is not null
        WebClient webClient = serviceProvider.getWebClient();
        assertNotNull(webClient);
    }
}
