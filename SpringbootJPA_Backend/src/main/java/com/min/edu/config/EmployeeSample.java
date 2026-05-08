package com.min.edu.config;

import java.util.List;
import java.util.stream.IntStream;

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
			//1. 첫번째 기본 값
//			Employee e1 = new Employee("hong", "gildong", "hong@sample.com");
//			Employee e2 = new Employee("jeon", "minkyun", "jeon@sample.com");
//			employeeRepository.saveAll(List.of(e1, e2));
			
//			//2. 기존 데이터에 값 추가
//			IntStream.rangeClosed(1, 100).forEach(i->{
//				Employee emp = new Employee();
//				emp.setFirstName("FirstName" + i);
//				emp.setLastName("LastName" + i);
//				emp.setEmailId("user"+i+"@example.com");
//				
//				employeeRepository.save(emp);
//			});
		};
	}
}
