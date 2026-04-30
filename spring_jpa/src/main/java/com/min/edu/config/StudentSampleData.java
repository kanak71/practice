package com.min.edu.config;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;

import com.min.edu.StudentRepository;
import com.min.edu.entity.StudentEntity;

//TODO 006 Spring Boot 시작 시 CommandLineRunner를 통해서 실행되는 것, 샘플 데이터 입력
//	Repository를 통해서 saveAll을 통해 여러개의 데이터를 입력한다
//	입력 객체는 Entity와 같은 모양이어야 한다

public class StudentSampleData {
	
	//StudentRepository Bean(@Repository)이다. 다른 bean에서도 사용할 수 있다(ref)
	//생성자, Setter, 멤버필드, 메소드
	@Bean
	CommandLineRunner commandLineRunner(StudentRepository repository) {
		return args->{
			//TODO 007 생성자(4개)를 통해서 age, email, name, dob를 입력한다
//			StudentEntity stu01 = new StudentEntity(20, "banana@gmail.com", "banana", LocalDate.of(2020, Month.JANUARY, 2));
//			StudentEntity stu02 = new StudentEntity(10, "tomato@gmail.com", "tomato", LocalDate.of(2021, Month.JULY, 4));
//			repository.saveAll(List.of(stu01, stu02));
			
			//TODO 010 생성자를 통해서 email, name, dob
			StudentEntity stu01 = new StudentEntity("banana@gmail.com", "banana", LocalDate.of(2020, Month.JANUARY, 2));
			StudentEntity stu02 = new StudentEntity("tomato@gmail.com", "tomato", LocalDate.of(2021, Month.JULY, 4));
			repository.saveAll(List.of(stu01, stu02));
		};
		
		
	}
	
}
