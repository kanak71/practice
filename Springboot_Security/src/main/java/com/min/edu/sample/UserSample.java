package com.min.edu.sample;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.min.edu.domain.UserEntity;
import com.min.edu.repository.UserRepository;

//TODO 006 샘플 데이터, 기본 password, 암호화된 password
@Configuration
public class UserSample {

	@Bean
	CommandLineRunner commandLineRunner(UserRepository repository) {
		return args ->{
//			UserEntity e1 = new UserEntity("user", "1234", "USER");
//			UserEntity e2 = new UserEntity("admin", "$2y$04$v4aQNjh0EbzblkfYoTzBqOXg.TU1Bz3YrBbCybiZ63H1w48KN7tFu", "ADMIN");
//			repository.saveAll(List.of(e1,e2));
		};
	}
}
