package com.stackroute.gipherrecommendersystem.config;

import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import brave.sampler.Sampler;

// import brave.sampler.Sampler;
/**
 * Slueth Sampler with ALWAYS_SAMPLE
 * 
 * The Sampler Not Required. Since We added property  spring.sleuth.sampler.probability: 1.0.
 * 
 * (In Spring Boot 2.1.2.RELEASE Not Required Bean Configuration, incase if we add this property)
 * 
 * This is always Sampler
 * 
 * @author L.GANESH
 *
 */

@Configuration

public class SluethSamplerConfiguration {

	@Bean
	public Sampler defaultSampler() {
		return Sampler.ALWAYS_SAMPLE;
	}
	

}

