package com.min.edu.sample;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.min.edu.repository.CarRespository;
import com.min.edu.repository.OwnerRepository;
import com.min.edu.vo.Car;
import com.min.edu.vo.Owner;

@Configuration
public class CarSample {
	
	@Bean
	CommandLineRunner commandLineRunner(CarRespository carRespository, OwnerRepository ownerRepsitory) {
		return args -> {
			
//			Owner owner1 = new Owner("Marry", "Chistmas");
//			Owner owner2 = new Owner("Jone", "Robinson");
//			
//			
//			Car ford = new Car(
//			        "Ford", "Mustang", "Red", "12가3456","2024", 55000000, owner1
//			);
//
//			Car hyundai = new Car(
//			        "Hyundai", "Sonata","Black","34나5678","2023", 32000000 , owner2
//			);
//
//			Car ray = new Car(
//			        "Kia", "Ray","White", "56다7890", "2022",  18000000 , owner2
//			);
//			ownerRepsitory.saveAll(List.of(owner1, owner2));
//			carRespository.saveAll(List.of(ford, hyundai, ray));
//			
		};
	}

}
