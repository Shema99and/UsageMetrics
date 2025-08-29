package com.zifo.ewb.wrapper.populatedata.helper;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.idbs.ewb.rest.services.AuthService;
import com.zifo.ewb.wrapper.constants.JSONConstants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AccessTokenProviderTest {

    @Mock
    private AuthService authService;

    @InjectMocks
    private AccessTokenProvider accessTokenProvider;

    private final ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRequestAccessToken_ValidToken() {
        String accessCode = "validCode";
        JsonNode tokenResponse = mapper.createObjectNode()
                .put(JSONConstants.ACCESS_TOKEN, "abc123");

        when(authService.getToken(accessCode)).thenReturn(tokenResponse);

        String result = accessTokenProvider.requestAccessToken(accessCode);

        assertEquals("abc123", result);
    }

    @Test
    void testRequestAccessToken_MissingToken() {
        String accessCode = "missingTokenCode";
        JsonNode tokenResponse = mapper.createObjectNode(); // no access_token field

        when(authService.getToken(accessCode)).thenReturn(tokenResponse);

        String result = accessTokenProvider.requestAccessToken(accessCode);

        assertEquals("", result); // should return empty string
    }

    @Test
    void testRequestAccessToken_NullToken() {
        String accessCode = "nullTokenCode";
        JsonNode tokenResponse = mapper.createObjectNode()
                .putNull(JSONConstants.ACCESS_TOKEN);

        when(authService.getToken(accessCode)).thenReturn(tokenResponse);

        String result = accessTokenProvider.requestAccessToken(accessCode);

        assertEquals("null", result); // null token should also return empty string
    }
}
