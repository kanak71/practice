package com.min.edu;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.min.edu.aop01.ITime;
import com.min.edu.aop01.Night;

@SpringBootTest
class Springboot03AopApplicationTests {

	//TODO 005 AOP를 통해 CC(ITime - Day, Night)
	//		004(Advice)를 통해서 실행
	
//	@Autowired
//	private ITime day;
//	
//	@Test
//	void contextLoads() {
//		day.clock();
//	}
	
	//TODO 007 VMI 동작확인
	//Spring CGLIB 클래스 기반으로 프록시(Proxy)를 사용하기 때문에 조심해야한다
	// "즉, interface 없이도 AOP를 동작시킬 수 있다"
	//		("execution(public * com.min.edu.aop01..*(..))")
	
	
	/*
	 * JDK Proxy : 인터페이스 기반
	 * CGLIB Proxy : 클래스 기반
	 */
	@Autowired
	private Night night;
	
	@Test
	public void nightTest() {
		night.use("VMI");
	}

}
