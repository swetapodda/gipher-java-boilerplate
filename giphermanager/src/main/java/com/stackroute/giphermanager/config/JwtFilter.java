package com.stackroute.giphermanager.config;


import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.GenericFilterBean;

import com.stackroute.giphermanager.IConstants;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

/**
 * This is Custom JWT Filter to validate all incomming Request
 * 
 * @author L.GANESH
 *
 */

public class JwtFilter extends GenericFilterBean {

	
	static {
		IConstants.generateExclueURIs();
	}
	
	
	
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

    	HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		HttpServletResponse httpServletResponse = (HttpServletResponse) response;
		final String authorizationHeader = httpServletRequest.getHeader(IConstants.HEADER.AUTHORIZATION);
		
		String requestURI=httpServletRequest.getRequestURI();
		/*
		System.out.println("authorizationHeader:"+authorizationHeader);
		System.out.println("requestURI:"+requestURI);
		System.out.println("IConstants.excludeURI:"+IConstants.excludeURI);
		System.out.println("Contains:"+IConstants.excludeURI.contains(requestURI.toLowerCase()));
		*/
		if (IConstants.excludeURI.contains(requestURI.toLowerCase()) ) {// Exclude UnAuthorized URLs. For ex: / should return "Service Info"
			System.out.println("Excluded");
			chain.doFilter(httpServletRequest, httpServletResponse);
		} else if (IConstants.HEADER.OPTIONS.equals(httpServletRequest.getMethod())) {
			System.out.println("Options");
			httpServletResponse.setStatus(HttpServletResponse.SC_OK);
			chain.doFilter(httpServletRequest, httpServletResponse);
		} else {
			System.out.println("Validating JWT");
			if (authorizationHeader == null || !authorizationHeader.startsWith(IConstants.HEADER.BEARER_KEY)) {
				System.out.println("Authorization Header is empty");
				String errorMessage = IConstants.HEADER.AUTHORIZATION_HEADER_MISSING;
				((HttpServletResponse) response).setHeader("X-Error-Message", errorMessage);
				((HttpServletResponse) response).sendError(HttpServletResponse.SC_FORBIDDEN, errorMessage);
			} else {
				System.out.println("Passed.. Rechecking..");
				String jwtToken = authorizationHeader.substring(IConstants.HEADER.BEARER_KEY.length());// Need to exclude: BEARER_KEY:
				Claims claims = Jwts.parser().setSigningKey(IConstants.HEADER.SECRET_KEY).parseClaimsJws(jwtToken).getBody();
				request.setAttribute("claims", claims);
				chain.doFilter(httpServletRequest, httpServletResponse);
			}

		}
    	
    }
}
