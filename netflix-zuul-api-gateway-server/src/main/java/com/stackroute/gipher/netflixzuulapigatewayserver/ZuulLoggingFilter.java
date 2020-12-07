package com.stackroute.gipher.netflixzuulapigatewayserver;


import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

/**
 *  Implement Zuul logging filter by extending zuul filter, So that we can inclue JWT header tokens for Zuul Requests.
 */

@Component
public class ZuulLoggingFilter  extends ZuulFilter{

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public boolean shouldFilter() {
		logger.debug("shouldFilter()--> This returns true");
		return true;
	}

	@Override
	public Object run() throws ZuulException {
		System.out.println("run(): PRE_DECORATION_FILTER_ORDER");
		RequestContext ctx = RequestContext.getCurrentContext();
		HttpServletRequest request = ctx.getRequest();
		//System.out.println("Request From:\t"+request.getRemoteHost()+"\tRequest Method : \t" + request.getMethod() + "\t Request URL : \t" + request.getRequestURL().toString());
		logger.info("Request From:\t"+request.getRemoteHost()+"\tRequest Method : \t" + request.getMethod() + "\t Request URL : \t" + request.getRequestURL().toString());
		 Enumeration<?> headerEnum=ctx.getRequest().getHeaderNames();
		 //Pushing Request Header Details to Zuul Header
		 while(headerEnum.hasMoreElements()) {
			 Object t=headerEnum.nextElement();
			 System.out.println(t+":"+ctx.getRequest().getHeader((String)t));
			 if( ! "content-length".equalsIgnoreCase((String)t)) {
				 ctx.addZuulRequestHeader((String)t,ctx.getRequest().getHeader((String)t));
			 }
		 }
		 
		return null;
	}

	@Override
	public String filterType() {
		logger.debug("Filter filterType(): PRE_DECORATION_FILTER_ORDER");
		return FilterConstants.PRE_TYPE;
	}

	@Override
	public int filterOrder() {
		logger.debug("filterOrder():"+FilterConstants.PRE_DECORATION_FILTER_ORDER);
		return FilterConstants.PRE_DECORATION_FILTER_ORDER;

	}

	
}
