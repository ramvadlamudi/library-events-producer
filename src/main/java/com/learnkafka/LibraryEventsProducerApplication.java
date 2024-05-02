package com.learnkafka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication(exclude = {UserDetailsServiceAutoConfiguration.class})
@PropertySource(value = "classpath:common.properties")
@PropertySource(value = "file:/app-config/application.properties",ignoreResourceNotFound = true)
public class LibraryEventsProducerApplication  extends SpringBootServletInitializer {
	public static void main(String[] args) {
		SpringApplication.run(LibraryEventsProducerApplication.class, args);
	}

}
