package com.zifo.ewb.wrapper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication(exclude = {SecurityAutoConfiguration.class, UserDetailsServiceAutoConfiguration.class})
@EnableScheduling

@ComponentScan(basePackages = {
    "com.zifo",
    "com.idbs"
})
public class WrapperApplication {

	public static void main(String[] args) {
		SpringApplication.run(WrapperApplication.class, args);
	}
}
