package com.min.edu.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.Setter;

//TODO 002 Owner Entity를 정의하여
//다대다관계 테이블은 owner_car로 정의
//Owner id와 관계 컬럼 owner_id로 정의
//Car id와 관계 컬럼 car_id로 정의

@Entity
@Setter
@Getter
public class Owner {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	
	@ManyToMany(cascade = {CascadeType.PERSIST,
							CascadeType.MERGE,
							CascadeType.REMOVE})
	
	@JoinTable(
				name="owner_car",
				joinColumns = @JoinColumn(name="owner_id"),
				inverseJoinColumns = @JoinColumn(name = "car_id")
			)
	private Set<Car> cars = new HashSet<Car>();
	
	
}
