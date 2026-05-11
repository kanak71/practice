package com.min.edu;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.min.edu.domain.UserEntity;
import com.min.edu.service.IUserService;

@SpringBootTest
class SpringbootSecurityApplicationTests {
	
	@Autowired
	private IUserService service;

	//TODO 007 JPA id조회, 입력 테스트
	@Test
	void contextLoads() {
		//Secutiry 없는 조회
		UserEntity outUser = service.getUserInfo("user").get();
		assertNotNull(outUser);
		
		//Secutiry 없는 입력
		UserEntity inUser = new UserEntity("text","1234","USER");
		UserEntity regUser = service.register(inUser);
		assertNotNull(regUser);
	}

}
