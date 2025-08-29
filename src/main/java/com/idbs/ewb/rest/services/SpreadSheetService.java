package com.idbs.ewb.rest.services;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.idbs.ewb.services.ServiceProvider;
import com.zifo.ewb.wrapper.constants.JSONConstants;
import com.zifo.ewb.wrapper.constants.ServiceConstants;

import lombok.RequiredArgsConstructor;

/**
 * The SpreadSheet Service is responsible for managing all non-cached entity
 * related requests
 * 
 * @author zifo
 * 
 */
@Service
@RequiredArgsConstructor
public class SpreadSheetService {

	/**
	 * Logger instance to write logs
	 * 
	 */

	private static final Logger LOGGER = LogManager.getLogger(SpreadSheetService.class);

	private final ServiceProvider serviceProvider;

	public String getData(final String versionId, final String inputJson, final String accessToken) {
		WebClient webClient = serviceProvider.getWebClient();
		String response;
		try {
			response = webClient.post()
					.uri(uriBuilder -> uriBuilder.path(ServiceConstants.SPREADSHEET_DATA)
							.queryParam(JSONConstants.VERSION_ID, versionId).build())
					.header(ServiceConstants.AUTHORIZATION, ServiceConstants.BEARER + accessToken).bodyValue(inputJson)
					.retrieve().bodyToMono(String.class).block(); 
		} catch (Exception e) {
			LOGGER.error("Error while calling spreadsheet service", e);
			response = StringUtils.EMPTY;
		}
		return response;
	}
}
