package com.stackroute.giphermanager.config;

import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import brave.sampler.Sampler;

// import brave.sampler.Sampler;
/**
 * Slueth Sampler with ALWAYS_SAMPLE
 * 
 * The Sampler Not Required. If We add property  spring.sleuth.sampler.probability: 1.0, then we can comment Sampler Bean Generation and this is always Sampler
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

