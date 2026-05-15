package com.min.edu.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name="CAR")
@Entity
@Getter
@Setter
@NoArgsConstructor
public class Car {	//JPA에 의해서 생성되는 테이블명지정
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name="BRAND")
	private String brand;
	@Column(name="MODEL")
	private String model;
	@Column(name="COLOR")
	private String color;
	@Column(name="REGISTERNUMBER")
	private String registerNumber;
	@Column(name="PRODUCTYEAR")
	private String productYear;
	@Column(name="PRICE")
	private Integer price;
	
	
	
	//id를 제외한 생성자 오버로딩
	public Car(String brand, String model, String color, String registerNumber, String productYear, Integer price) {
		super();
		this.brand = brand;
		this.model = model;
		this.color = color;
		this.registerNumber = registerNumber;
		this.productYear = productYear;
		this.price = price;
	}
	
	

}
