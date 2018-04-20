package com.cafe24.mysite.aspect;


import org.apache.catalina.tribes.util.Arrays;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class LogAspect {

	private static final Logger logger = LoggerFactory.getLogger(LogAspect.class);
	
	@Before("execution(* *..service.*.*(..))")
	public void startLog(JoinPoint jp) {
		 // 핵심업무의 클래스,매서드, 매개변수 로깅 
        logger.info("핵심업무 코드정보  : "+jp.getSignature());
        logger.info("메서드 : "+jp.getSignature().getName());
        logger.info("매개변수:"+Arrays.toString(jp.getArgs()));
	}
	
	  @Around("execution(* *..service.*.*(..))")
	    public Object timeLog(ProceedingJoinPoint pjp) throws Throwable {
	        // 핵심업무 실행 전
	        long start = System.currentTimeMillis(); 
	        // 핵심업무 실행
	        Object result = pjp.proceed(); 
	        // 핵심업무 실행 후
	        long end = System.currentTimeMillis(); 
	        // 핵심업무 실행시간 연산
	        logger.info(pjp.getSignature().getName()+"메서드 실행시간:"+(end-start));
	        logger.info("==========================================");
	        return result;
	    }
}
