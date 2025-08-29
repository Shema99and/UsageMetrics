package com.zifo.ewb.wrapper.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zifo.ewb.wrapper.exceptions.ValueNotFoundException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.NoArgsConstructor;

/**
 * @author zifo
 *
 */

@RestController
@NoArgsConstructor
@RequestMapping("/API/v1")
@Api(tags = "Health API")

public class Health {
	@Value("${idbs.host}")
	private String host;


	private static final Logger LOGGER = LogManager.getLogger(Health.class);

	/**
	 * @return
	 * @throws ValueNotFoundException
	 */
	@ApiOperation(value = "Check the health of the API")
	@GetMapping("/healthCheck")
	public String healthCheck() {
		LOGGER.debug(host);
		return "Usage Metrics API is up and running for " + host + " server";
	}
}
