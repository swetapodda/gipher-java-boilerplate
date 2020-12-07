package com.stackroute.accountmanager.aspectj;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/*
 * Aspect for Logging
 */
@Aspect
@Component
public class LoggingAspect {
	
	private Logger log = LoggerFactory.getLogger(getClass());

	@Before(value="execution(* com.stackroute.accountmanager.controller.*.*(..))")
	public void beforeNoteControllerAdvice(JoinPoint joinPoint) {
		log.info(joinPoint.getTarget().getClass()+ " Before All. Method ->:"+joinPoint.getSignature());
	}

	@After(value="execution(* com.stackroute.accountmanager.controller.*.*(..))")
	public void afterNoteControllerAdvice(JoinPoint joinPoint) {
		log.info(joinPoint.getTarget().getClass()+ " After all mehtod -->:"+joinPoint.getSignature());

	}
	
	@AfterReturning(value="execution(* com.stackroute.accountmanager.controller.*.*(..))")
	public void afterRunningNoteControllerAdvice(JoinPoint joinPoint) {
		log.info(joinPoint.getTarget().getClass()+ " After Runing all mehtod ->:"+joinPoint.getSignature());

	}
	
	@AfterThrowing(pointcut="within(com.stackroute.accountmanager.controller.*)", throwing="exceptinon")
	public void afterThrowingNoteControllerAdvice(JoinPoint joinPoint) {
		log.info(joinPoint.getTarget().getClass()+ " After Throwing all mehtod->:"+joinPoint.getSignature());

	}
}
