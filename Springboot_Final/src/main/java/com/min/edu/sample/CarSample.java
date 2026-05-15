package com.min.edu.sample;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.min.edu.repository.CarRepository;
import com.min.edu.vo.Car;

@Configuration
public class CarSample {

	@Bean
	CommandLineRunner commandLineRunner(CarRepository carRepository) {
		return args -> {
//			Car ford = new Car(
//			        "Ford",
//			        "Mustang",
//			        "Red",
//			        "12가3456",
//			        "2024",
//			        55000000
//			);
//
//			Car hyundai = new Car(
//			        "Hyundai",
//			        "Sonata",
//			        "Black",
//			        "34나5678",
//			        "2023",
//			        32000000
//			);
//
//			Car ray = new Car(
//			        "Kia",
//			        "Ray",
//			        "White",
//			        "56다7890",
//			        "2022",
//			        18000000
//			); 
//			carRepository.saveAll(List.of(ford, hyundai, ray));
		};
	}
	
}
