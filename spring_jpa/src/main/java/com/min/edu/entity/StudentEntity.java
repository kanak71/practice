package com.min.edu.entity;

import java.time.LocalDate;
import java.time.Period;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Transient;
import lombok.Data;
import lombok.NoArgsConstructor;

//TODO 001 DB에 테이블로 만들어진 객체 => ORM(Object Relation Mapping)

@Data
@NoArgsConstructor
@Entity // 자동으로 application.properties 정보의 Datasource를 사용해서 멤버필드를 annotation에 맞춰 테이블로 만들어 줌
public class StudentEntity {

	@Id
	@SequenceGenerator(name="student_sequence", sequenceName = "student_sequence", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "student_sequence")
	private Long id;
	
	//TODO 008 @Transient를 통해서 age는 생년월일(dob)를 통해서 연산되어 처리되기 때문에 Entity 제외한다
	@Transient
	private Integer age;
	private String name;
	private String email;
	private LocalDate dob;
	
	// Main과 DB없이 테스트 할때 사용
	public StudentEntity(Long id, Integer age, String name, String email, LocalDate dob) {
		super();
		this.id = id;
		this.age = age;
		this.name = name;
		this.email = email;
		this.dob = dob;
	}

	// DB 연결후 JPA를 통해서 자동으로 ID를 등록 테스트를 할 때 사용
	public StudentEntity(Integer age, String name, String email, LocalDate dob) {
		super();
		this.age = age;
		this.name = name;
		this.email = email;
		this.dob = dob;
	}

	// Entity를 통해서 컬럼에서 제외 하고 (Transient 제외) age를 dob에서 계산해서 입력
	public StudentEntity(Long id, String name, String email, LocalDate dob) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.dob = dob;
	}

	// 자동생성되는 ID와 연산 출력시 사용하는 age를 제외하고 입력 할때 사용
	public StudentEntity(String name, String email, LocalDate dob) {
		super();
		this.name = name;
		this.email = email;
		this.dob = dob;
	}
	
	// TODO 009 생년월일 연산 dob에서 현재 날짜를 통해서 나이를 계산 메소드
	public Integer getAge() {
		return Period.between(this.dob, LocalDate.now()).getYears();
	}
	
	
	
	
	
	
}







