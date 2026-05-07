package com.min.edu.config;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.min.edu.model.Employee;
import com.min.edu.repository.EmployeeRepository;

@Configuration
public class EmployeeSample {

	@Bean
	CommandLineRunner commandLineRunner(EmployeeRepository employeeRepository) {
		return args ->{
			Employee e1 = new Employee("hong", "gildong", "hong@sample.com");
			Employee e2 = new Employee("jeon", "minkyun", "jeon@sample.com");
			employeeRepository.saveAll(List.of(e1, e2));
		};
	}
}
