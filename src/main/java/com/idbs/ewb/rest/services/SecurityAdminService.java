package com.idbs.ewb.rest.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.fasterxml.jackson.databind.JsonNode;
import com.idbs.ewb.services.ServiceProvider;
import com.zifo.ewb.wrapper.constants.ServiceConstants;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SecurityAdminService {
	private final ServiceProvider serviceProvider;
	
    @Value("${entity_auth}") 
    private String authHeader;

    private static final Logger LOGGER = LogManager.getLogger(SecurityAdminService.class);
	
	public JsonNode getUsers() {
	    try {
	        return serviceProvider.getWebClient().get()
	            .uri(uriBuilder -> uriBuilder.path("/security/administration/users").build())
	            .headers(headers -> 
	                headers.set(HttpHeaders.AUTHORIZATION, ServiceConstants.BASIC + authHeader)
	            )
	            .retrieve()
	            .bodyToMono(JsonNode.class)
	            .block();
	    } catch (WebClientResponseException ex) {
	        LOGGER.error("WebClientResponseException: {} - {}", ex.getStatusCode(), ex.getResponseBodyAsString());
	        return null;
	    } catch (Exception ex) {
	    	LOGGER.error("Exception while calling getUsers: {}", ex.getMessage(), ex);
	        return null;
	    }
	}
	
	public JsonNode findGroupsForUser(String username) {
		return serviceProvider.getWebClient().get()
		.uri("/security/administration/users/{username}/groups", username)
		.headers(headers -> 
            headers.set(HttpHeaders.AUTHORIZATION,ServiceConstants.BASIC+authHeader)
        )
		.retrieve()
		.bodyToMono(JsonNode.class)
		.block();
	}
}
