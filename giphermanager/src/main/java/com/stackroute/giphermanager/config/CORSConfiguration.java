package com.stackroute.giphermanager.config;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
/**To fix the following Error.
 * Access to XMLHttpRequest at 'http://localhost:9200/api/v1/giphermanager/trendGiphies?pageNo=4' from origin 'http://localhost:4200' has been blocked by CORS policy: No 'Access-Control-Allow-Origin' header is present on the requested resource.
 * @author L.GANESH
 *
 */
@Component
public class CORSConfiguration {
	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/api/v1/giphermanager").allowedOrigins("*").allowedMethods("GET", "POST","PUT", "DELETE");// From all host and only with /api/v1/accountmanager based Requests.
				registry.addMapping("/**").allowedOrigins("*").allowedMethods("GET", "POST","PUT", "DELETE"); //Note; If we not given allowed Mehtods, post/get works But DELETE blocked by CORS
			}
		};
	}
}
