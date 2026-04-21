package com.min.edu.aop;

import org.aspectj.lang.JoinPoint;
import org.springframework.stereotype.Component;
import java.util.Arrays;

import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

import lombok.extern.slf4j.Slf4j;

//TODO 006 *Mapper*가 실행될때 동작되는 AOP
//		1) CC 이전에 실행되는 Before : 전달되는 Arugment를 출력
//		2) CC가 오류가 발생했을때 오류 메시지 처리하는 AfterThrowing
@Component
@Aspect
@Slf4j
public class MapperAop {

	@Pointcut("execution(public * com.min.edu.model.mapper.*Mapper*.*(..))")
	public void pointCut() {}
	
	@Before(value="pointCut()")
	public void before(JoinPoint j) {
		log.info("\n\n");
		log.info("AOP 메소드 시작전");
		log.info("AOP 사용되는 CC 메소드 {}", j.getSignature().getName());
		Object[] args = j.getArgs();
		if(args != null) {
			log.info("\t Args: {}", Arrays.toString(args));
		}
		log.info("\n\n");
	}
	@AfterThrowing(value = "pointCut()", throwing = "e")
	public void afterThrowing(JoinPoint j, Exception e) {
		log.info("\n\n");
		log.info("AOP 사용되는 CC 메시지 {}", j.getSignature().getName());
		log.info("AOP 오류 메시지{}", e.getMessage());
		log.info("\n\n");
	}
}
