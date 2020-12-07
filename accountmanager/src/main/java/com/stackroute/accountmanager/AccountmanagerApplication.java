package com.stackroute.accountmanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.context.annotation.Bean;

import com.stackroute.accountmanager.config.JwtFilter;

/**
 * Account Manager Micro Service Spring Boot Application
 * @author L.GANESH
 *
 */

@SpringBootApplication
//@EnableAspectJAutoProxy
@EnableDiscoveryClient
@RibbonClient(name = "account-manager-servers")//, configuration = RibbonConfiguration.class)
//@ComponentScan({"com.stackroute.accountmanager"})
//@ComponentScan("com.stackroute.accountmanager")
public class AccountmanagerApplication {

	/**
	 * JWT Filter Bean Configuration
	 */
	@Bean
	public FilterRegistrationBean<JwtFilter> jwtFilter() {

		FilterRegistrationBean<JwtFilter> filterRegistrationBean = new FilterRegistrationBean<>();
		filterRegistrationBean.setFilter(new JwtFilter());
		filterRegistrationBean.addUrlPatterns("/api/v1/accountmanager/*");
		
		return filterRegistrationBean;
	}
	public static void main(String[] args) {
		SpringApplication.run(AccountmanagerApplication.class, args);
	}
	
}

