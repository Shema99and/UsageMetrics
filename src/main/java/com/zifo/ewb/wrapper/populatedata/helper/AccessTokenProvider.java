package com.zifo.ewb.wrapper.populatedata.helper;

import java.text.MessageFormat;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.idbs.ewb.rest.services.AuthService;
import com.zifo.ewb.wrapper.constants.JSONConstants;

import lombok.RequiredArgsConstructor;

/**
 * Class that provides the Access Token
 * 
 * @author zifo
 *
 */
@Service
@RequiredArgsConstructor
public final class AccessTokenProvider {

	private final AuthService authService;

	/**
	 * Logger instance to write logs
	 */
	private static final Logger LOGGER = LogManager.getLogger(AccessTokenProvider.class);

	/**
	 * This method is requesting for access token
	 * 
	 * @param accessCode
	 * @return
	 */
	public String requestAccessToken(final String accessCode) {

		final JsonNode tokenResponse = authService.getToken(accessCode);
		final JsonNode path = tokenResponse.path(JSONConstants.ACCESS_TOKEN);
		LOGGER.debug(MessageFormat.format("token response: {0}", tokenResponse));
		LOGGER.debug(MessageFormat.format("path: {0}", path));
		return this.getAccessToken(path);
	}

	/**
	 * Method that gets access token
	 * 
	 * @param accessTokenJson
	 * @return
	 */
	private String getAccessToken(final JsonNode accessTokenJson) {
		String accessToken;
		if (!accessTokenJson.isMissingNode()) {
			accessToken = accessTokenJson.asText();
		} else {
			accessToken = StringUtils.EMPTY;
		}
		return accessToken;
	}
}
