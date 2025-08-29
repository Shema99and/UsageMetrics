package com.idbs.ewb.rest.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.RequestHeadersSpec;
import org.springframework.web.reactive.function.client.WebClient.RequestHeadersUriSpec;
import org.springframework.web.reactive.function.client.WebClient.ResponseSpec;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.idbs.ewb.services.ServiceProvider;

import reactor.core.publisher.Mono;

class SecurityAdminServiceTest {
    @Mock
    private ServiceProvider serviceProvider;
    @Mock
    private WebClient webClient;
    @Mock
    private RequestHeadersUriSpec uriSpec;
    @Mock
    private RequestHeadersSpec headersSpec;
    @Mock
    private ResponseSpec responseSpec;

    @InjectMocks
    private SecurityAdminService securityAdminService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        ReflectionTestUtils.setField(securityAdminService, "authHeader", "testAuthHeader");
    }

    @Test
    void testGetUsersReturnsJsonNode() {
        JsonNode expectedNode = JsonNodeFactory.instance.objectNode();
        when(serviceProvider.getWebClient()).thenReturn(webClient);
        when(webClient.get()).thenReturn(uriSpec);
        when(uriSpec.uri(any(java.util.function.Function.class))).thenReturn(headersSpec);
        when(headersSpec.headers(any())).thenReturn(headersSpec);
        when(headersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(JsonNode.class)).thenReturn(Mono.just(expectedNode));

        JsonNode result = securityAdminService.getUsers();
        assertNotNull(result);
        assertEquals(expectedNode, result);
    }

    @Test
    void testFindGroupsForUserReturnsJsonNode() {
        JsonNode expectedNode = JsonNodeFactory.instance.objectNode();
        when(serviceProvider.getWebClient()).thenReturn(webClient);
        when(webClient.get()).thenReturn(uriSpec);
        when(uriSpec.uri(anyString(), any(Object[].class))).thenReturn(headersSpec);
        when(headersSpec.headers(any())).thenReturn(headersSpec);
        when(headersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(JsonNode.class)).thenReturn(Mono.just(expectedNode));

        JsonNode result = securityAdminService.findGroupsForUser("testuser");
        assertNotNull(result);
        assertEquals(expectedNode, result);
    }
}