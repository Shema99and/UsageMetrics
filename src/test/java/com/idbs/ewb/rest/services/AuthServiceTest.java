package com.idbs.ewb.rest.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.net.URI;
import java.util.function.Function;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.idbs.ewb.services.ServiceProvider;

import reactor.core.publisher.Mono;

class AuthServiceTest {

    @Mock
    private ServiceProvider serviceProvider;

    @Mock
    private WebClient webClient;

    @Mock
    private WebClient.RequestBodyUriSpec requestBodyUriSpec;

    @Mock
    private WebClient.RequestHeadersSpec<?> requestHeadersSpec;

    @Mock
    private WebClient.RequestHeadersUriSpec<?> requestHeadersUriSpec;

    @Mock
    private WebClient.ResponseSpec responseSpec;

    @InjectMocks
    private AuthService authService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Set values for @Value fields manually
        authService = new AuthService(serviceProvider);
        ReflectionTestUtils.setField(authService, "grantType", "authorization_code");
        ReflectionTestUtils.setField(authService, "redirectURI", "http://localhost/callback");
        ReflectionTestUtils.setField(authService, "clientId", "client123");
        ReflectionTestUtils.setField(authService, "tokenURL", "/token");
        ReflectionTestUtils.setField(authService, "authorization", "Basic abc123");
        ReflectionTestUtils.setField(authService, "password", "abc123");
    }

    @Test
    void testGetTokenSuccess() {
        String code = "testCode";
        JsonNode expectedResponse = new ObjectMapper().createObjectNode().put("access_token", "xyz");

        when(serviceProvider.getWebClient()).thenReturn(webClient);
        when(webClient.post()).thenReturn(requestBodyUriSpec);
        when(requestBodyUriSpec.uri(ArgumentMatchers.<Function<UriBuilder, URI>>any()))
            .thenReturn(requestBodyUriSpec);
        when(requestBodyUriSpec.header(anyString(), anyString())).thenReturn(requestBodyUriSpec);
        when(requestBodyUriSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(JsonNode.class)).thenReturn(Mono.just(expectedResponse));

        

        JsonNode actualResponse = authService.getToken(code);

        assertEquals("xyz", actualResponse.get("access_token").asText());
    }

    @Test
    void testGetTokenException() {
        String code = "testCode";

        when(serviceProvider.getWebClient()).thenReturn(webClient);
        when(webClient.post()).thenThrow(new RuntimeException("Connection error"));

        JsonNode actualResponse = authService.getToken(code);

        assertTrue(actualResponse.isMissingNode());
    }
}
