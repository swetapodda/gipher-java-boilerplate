package com.stackroute.accountmanager.config;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Component
public class CORSConfiguration {
	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/api/v1/accountmanager/").allowedOrigins("*");// From all host and only with /api/v1/accountmanager based Requests.
				registry.addMapping("/**").allowedOrigins("*");
			}
		};
	}
}
