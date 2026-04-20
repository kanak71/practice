package com.min.edu.aop01;

import org.aopalliance.intercept.Joinpoint;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

//TODO 004 AOP 작성

/*
 * @Pointcut 	공통관심사(Advice)를 적용할때 실행되어야 할(Proxy) 메소드의 패턴 정의
 * 
 * @Before		대상메소드(CC) 실행 전에 실행됨
 * @After		대상메소드(CC) 실행 후에 실행됨
 * @Around		대상메소드(CC) 실행 전후에 실행됨	
 * @AfterReturning	대상메소드가 정상적으로 실행되고 리턴 값이 있을 경우 실행
 */
@Component
@Aspect
public class DayNightAspect {
	
	//Pointcut : CC를 찾음
	// aop01 패키지 내에 있는 모든 public 메소드
	@Pointcut("execution(public * com.min.edu.aop01..*(..))")
	public void dayNightPointCut() {}
	
	//advice란? CC를 실행시킬때 사용되늰 메소드 - joinPoint
	
	//메소드 실행전
	@Before("dayNightPointCut()")
	public void before() {
		System.out.println(" 낮이 시작되었습니다 - before");

	}
	
	@After("dayNightPointCut()")
	//메소드 실행후
	public void after() {
		System.out.println(" 밤이 시작되었습니다 - after");
	}
	
	//Around 낮과 밤 전체 시간 표시
	//*** before와 after가 있다. around가 있다. 누가 먼저 실행될까?
	//		결과 : Around가 before/atfer를 감싸서 실행
	@Around("dayNightPointCut()")
	public Object around(ProceedingJoinPoint joinPoint) {	//joinPoint CC의 정보 및 AOP의 Proxy 정보가 담겨 있음
		String methodName = joinPoint.getSignature().toShortString();
		System.out.println("하루 활동 시작 - Around");
		
		try {
			System.out.println(methodName + "실행중 - 낮 활동");
			Object result = joinPoint.proceed();
			
			System.out.println(methodName + "실행결과 :" + result + "-밤 활동 종료");
			return result;
		} catch (Throwable e) {
			System.out.println("예외 발생 : 활동 중단");
			e.printStackTrace();
			return null;
		}
		
	}
	
	//AfterReturning
	//반환이 있는 경우에 실행되는 Advice
	//value는 PointCut , returning은 값이 담기는 parameter 명을 작성
	@AfterReturning(value = "dayNightPointCut()", returning = "returnValue")
	public void afterReturning(JoinPoint joinPoint, Object returnValue) {
		System.out.println("하루 결과 확인: " + joinPoint.getSignature().getName());
		System.out.println("리턴 값: "+ returnValue);
		
		//before 작성하는 기능 : Arguments 를 출력
		Object[] args = joinPoint.getArgs();
		for(Object o : args) {
			System.out.println("활동을 위해서 사용하는 Arg :" + o.toString());
		}
	}
	
	
}







