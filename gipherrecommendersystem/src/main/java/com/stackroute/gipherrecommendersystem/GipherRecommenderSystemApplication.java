package com.stackroute.gipherrecommendersystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;

import com.stackroute.gipherrecommendersystem.stream.RecomendationMessageProcessor;
/**
 * Gipher Recomender System.
 * 
 * @EnableDiscoveryClient annotation not Required. By Default it looks local host, or eureka.client.service-url.defaultZone property
 * and By default register is true and can be controlled register-with-eureka flag.
 * 
 * @author L.GANESH
 *
 */
@SpringBootApplication
//@EnableDiscoveryClient
@EnableBinding(RecomendationMessageProcessor.class) //Enabling Stream Process
public class GipherRecommenderSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(GipherRecommenderSystemApplication.class, args);
	}

}

