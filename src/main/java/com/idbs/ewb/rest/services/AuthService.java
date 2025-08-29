package com.idbs.ewb.rest.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.MissingNode;
import com.idbs.ewb.services.ServiceProvider;
import com.zifo.ewb.wrapper.constants.PropConstants;
import com.zifo.ewb.wrapper.constants.ServiceConstants;

import lombok.RequiredArgsConstructor;

/**
 * Class that represents a authentication service to get the access token for
 * the given details
 * 
 * @author zifo
 *
 */
@Service
@RequiredArgsConstructor
public class AuthService {

	/**
	 * Singleton instance of the application properties reader class
	 */

	/**
	 * Logger instance to write logs
	 */
	private static final Logger LOGGER = LogManager.getLogger(AuthService.class);

	/**
	 * @param code
	 * @return
	 */

	private final ServiceProvider serviceProvider;
	@Value("${grant_type}")
	private String grantType;
	@Value("${redirect_uri}")
	private String redirectURI;
	@Value("${client_id}")
	private String clientId;
	@Value("${token_request_url}")
	private String tokenURL;
	@Value("${authorization}")
	private String authorization;
	@Value("${authorization}")
	private String password;

	public JsonNode getToken(final String code) {

		JsonNode response = MissingNode.getInstance();
		try {

			WebClient webClient = serviceProvider.getWebClient();

			response = webClient.post()
					.uri(uriBuilder -> uriBuilder.path(tokenURL).queryParam(PropConstants.GRANT_TYPE, grantType)
							.queryParam(ServiceConstants.CODE, code).queryParam(PropConstants.REDIRECT_URI, redirectURI)
							.queryParam(PropConstants.CLIENT_ID, clientId).build())
					.header(ServiceConstants.AUTHORIZATION, ServiceConstants.BASIC + password)
					.header(ServiceConstants.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
					.header(ServiceConstants.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE).retrieve()
					.bodyToMono(JsonNode.class).block();

		} catch (Exception e) {
			LOGGER.error("Error while fetching token: {}", e.getMessage(), e);
		}

		return response;
	}
}
