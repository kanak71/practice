package com.min.edu.aop01;

import org.springframework.stereotype.Component;

//TODO 003 ITime을 정의하는 Night, Bean
@Component
public class Night implements ITime {

	@Override
	public void clock() {
		System.out.println("밤시간 - 18:00 ~ 06:00");
	}
	
	// *** OCP 관련 Proxy 
	public String use(String action) {
		System.out.println("반환과 arguments가 있는 메소드 -CC");
		return action;
	}

}
