package com.stackroute.gipher.netflixzuulapigatewayserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;

import brave.sampler.Sampler;


/**
 *  Zuul API Gateway Micro Service.
 *  
 *  Included Sleuth Sampler.
 *  
 * @author L.GANESH
 *
 */

@SpringBootApplication
@EnableZuulProxy 
@EnableDiscoveryClient
public class NetflixZuulApiGatewayServerApplication {
	
	@Bean
	public Sampler defaultSampler(){
		return Sampler.ALWAYS_SAMPLE;
	}
	
	public static void main(String[] args) {
		SpringApplication.run(NetflixZuulApiGatewayServerApplication.class, args);
	}
}
