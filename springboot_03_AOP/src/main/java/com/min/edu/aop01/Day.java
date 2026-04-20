package com.min.edu.aop01;

import org.springframework.stereotype.Component;

//TODO 002 ITime을 정의하는 Day, Bean
@Component	//stereotype @Controller @Service @Repository
public class Day implements ITime {

	@Override
	public void clock() {
		System.out.println("낮시간 : 06:00 ~ 18:00");
	}

}
