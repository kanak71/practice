package com.min.edu.sample;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.min.edu.repository.CarRespository;
import com.min.edu.repository.OwnerRepsitory;
import com.min.edu.repository.UserRepository;
import com.min.edu.vo.Car;
import com.min.edu.vo.Owner;
import com.min.edu.vo.User;

@Configuration
public class CarSample {
	
	@Bean
	CommandLineRunner commandLineRunner(CarRespository carRespository, 
										OwnerRepsitory ownerRepsitory,
										UserRepository userRepository) {
		return args -> {
			
//			Owner owner1 = new Owner("Marry", "Chistmas");
//			Owner owner2 = new Owner("Jone", "Robinson");
//			Car ford = new Car("Ford", "Mustang", "Red", "12가3456","2024", 55000000, owner1);
//			Car hyundai = new Car("Hyundai", "Sonata","Black","34나5678","2023", 32000000 , owner2);
//			Car ray = new Car("Kia", "Ray","White", "56다7890", "2022",  18000000 , owner2);
//			ownerRepsitory.saveAll(List.of(owner1, owner2));
//			carRespository.saveAll(List.of(ford, hyundai, ray));
			
			
			// User 정보 입력
//			User u1 = new User("user", "$2y$04$kNQBXSnFiK0LaTm6N0aY7em6GSA57uA4NAnE.WujxiIdsMFdIQEfS", "USER");
//			User u2 = new User("admin", "$2y$04$kNQBXSnFiK0LaTm6N0aY7em6GSA57uA4NAnE.WujxiIdsMFdIQEfS", "ADMIN");
//			userRepository.saveAll(List.of(u1,u2));
			
			
		};
	}

}








