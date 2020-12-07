package com.stackroute.accountmanager.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.stackroute.IConstants;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
/**
 * Swagger configuration for API Documentation.
 * 
 * @author L.GANESH
 *
 */

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

	
	@Bean
    public Docket productApi() {
		 return new Docket(DocumentationType.SWAGGER_2).groupName("Gipher")
		  .apiInfo(apiInfo())
		  .select()
		  .paths(postPaths())
		  
		  .build()
		  .globalOperationParameters(getAuthorizationHeader());
		  
		
		 
    }
    @SuppressWarnings("unchecked")
	private Predicate<String> postPaths() {
		return Predicates.or(PathSelectors.regex("/api/v1/accountmanager.*"));
	}

	private ApiInfo apiInfo() {
	        ApiInfo apiInfo=new ApiInfoBuilder().title("Account Service")
				.description("Account Service API for Developers")
				.termsOfServiceUrl("http://lganesh.com")
				.contact(new Contact("L.GANESH","www.lganesh.com","ganlaksh@in.ibm.com"))
				.license("Apache License")
				.licenseUrl("www.lganesh.com/licence").version("1.0")
				.build();
	        
	        return apiInfo;
	}
	
	private List<Parameter> getAuthorizationHeader() {
		 ParameterBuilder parameterBuilder = new ParameterBuilder();
	        parameterBuilder.name("Authorization")
	                .modelRef(new ModelRef("string"))
	                .parameterType("header")
	                .description("JWT Token. Enter your token like \""+IConstants.HEADER.BEARER_KEY+"jwtToken\"")
	                //.required(true)
	                .defaultValue(IConstants.HEADER.BEARER_KEY+"your_toke")
	                .build();
	        List<Parameter> parameters = new ArrayList<>();
	        parameters.add(parameterBuilder.build());
	        return parameters;
	}

	
}
