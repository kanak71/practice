package com.min.edu.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name="CAR") // JPA에 의해서 생성되는 테이블명 지정
@Entity

@Getter
@Setter
@NoArgsConstructor
public class Car {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name="BRAND")
	private String brand;
	@Column(name = "MODEL")
	private String model;
	@Column(name="COLOR")
	private String color;
	@Column(name ="REGISTERNUMBER")
	private String registerNumber;
	@Column(name ="PRODUCTYEAR")
	private String productYear;
	@Column(name = "PRICE")
	private Integer price;
	
	
	// ---- owner를 검색하면 여러개의 Car가 조회된다
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="owner")
	private Owner owner;
	
	
	// 차동차 정보만 입력 하는 생성자
	public Car(String brand, String model, String color, String registerNumber, String productYear, Integer price) {
		super();
		this.brand = brand;
		this.model = model;
		this.color = color;
		this.registerNumber = registerNumber;
		this.productYear = productYear;
		this.price = price;
	}


	// 차동자와 Onwer 같이 입력하는 생성자
	public Car(String brand, String model, String color, String registerNumber, String productYear, Integer price,
			Owner owner) {
		super();
		this.brand = brand;
		this.model = model;
		this.color = color;
		this.registerNumber = registerNumber;
		this.productYear = productYear;
		this.price = price;
		this.owner = owner;
	}
	
	
	
	
	
} // 클래스 끝













