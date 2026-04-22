package com.min.edu.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

//TODO 006 Dao AOP : Before(아규먼트), After(종료), AfterThrowing(예외발생), Afterturning(반환출력)
@Component
@Aspect
@Slf4j
public class DaoLogAop {

	@Pointcut("execution(public * com.min.edu.model.mapper.*Dao*.*(..))")
	public void pointcut() {
		
	}
	
	@Before("pointCut()")
	public void before(JoinPoint j) {
		log.info("[AOP Logger] Dao 메소드 실행 전");
		Object[] args = j.getArgs();
		if(args != null || args.length != 0) {
			log.info("\t [AOP Logger] : {}", j.getSignature().getName());
			for (int i = 0; i < args.length; i++) {
				log.info("\t {}번째 : {}", i, args[i]);
			}
			log.info("\t [AOP Logger] : {}", j.getSignature().getName());
		}
	}
	
	@After("pointcut()")
	public void after(JoinPoint j) {
		log.info("[AOP Logger 종료 메소드명] : \t", j.getSignature().getName());
	}
	
	@AfterReturning(value = "pointcut()", returning = "returnValue")
	public void afterReturning(JoinPoint j, Object returnValue) {
		log.info("[AOP Logger 결과 반환 : \t {}", returnValue);
	}
	
	@AfterThrowing(value = "pointcut()", throwing = "error")
	public void afterThrowing(JoinPoint j , Exception error) {
		log.info("[AOP Logger 오류] : \t {}", error);
	}
}

	
