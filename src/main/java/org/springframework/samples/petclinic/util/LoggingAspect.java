package org.springframework.samples.petclinic.util;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggingAspect {
private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);
	
	public void logBefore(JoinPoint joinPoint) {
		logger.debug("logBefore()");
	}

	public void logAfter(JoinPoint joinPoint) {
		logger.debug("logAfter()");
	}

	/**
	 * 메소드 별 로그 앞뒤로 남기기
	 * @param joinPoint
	 * @return
	 * @throws Throwable
	 * @auther jp1020 2014. 1. 15.
	 */

	public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
		Object thisObj = joinPoint.getTarget();
		String className = thisObj.getClass().getName();
		Object[] args = joinPoint.getArgs();
		long currentTime = System.currentTimeMillis();

		if(logger.isDebugEnabled()){
			logger.debug("=================================================");
			logger.debug(">>>>>>>>> LOGGING START >>>>>>>>>>");
			logger.debug("[class]:" + className);
			logger.debug("[method]:" + joinPoint.getSignature().getName() + "()");
		}

		Object returnObj = joinPoint.proceed();
		if(logger.isDebugEnabled()){
			logger.debug("[class]:" + className);
			logger.debug("[method]:" + joinPoint.getSignature().getName() + "()");
			logger.debug("[소요시간]: {}ms", new Object[]{(System.currentTimeMillis()-currentTime)});
			logger.debug(">>>>>>>>>> LOGGING END >>>>>>>>>>");
			logger.debug("=================================================");
		}
		return returnObj;
	}
}
