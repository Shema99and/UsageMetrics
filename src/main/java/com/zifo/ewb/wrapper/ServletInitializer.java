package com.zifo.ewb.wrapper;


import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * Class which exposes the application as web service
 * 
 * @author zifo
 *
 */
public final class ServletInitializer extends SpringBootServletInitializer {

	private ServletInitializer() {
		super();
	}

	@Override
	protected SpringApplicationBuilder configure(final SpringApplicationBuilder application) {
		return application.sources(WrapperApplication.class);
	}

}
