package com.stackroute.giphermanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.context.annotation.Bean;

import com.stackroute.giphermanager.config.JwtFilter;
/**
 * Gipher Manager. Managing User favourite Giphy Images
 * 
 * @author L.GANESH
 *
 */
@SpringBootApplication
@RibbonClient(name = "gipher-manager-servers")
public class GipherManagerApplication {

	/*
	 * JWT Filter Bean for Filter Security
	 */
	@Bean
	public FilterRegistrationBean<JwtFilter> jwtFilter() {
		
		FilterRegistrationBean<JwtFilter> filterRegistrationBean = new FilterRegistrationBean<>();
		filterRegistrationBean.setFilter(new JwtFilter());
		filterRegistrationBean.addUrlPatterns("/api/v1/giphermanager/*");
		
		return filterRegistrationBean;
	}
	
	public static void main(String[] args) {
		SpringApplication.run(GipherManagerApplication.class, args);
	}

}

