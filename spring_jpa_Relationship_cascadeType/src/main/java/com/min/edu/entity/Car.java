package com.min.edu.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.Setter;

//TODO 001 Car Entity 생성 mappedBy 설정을 통해서 자식 테이블로 정의
@Entity
@Getter
@Setter
public class Car {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	
	@ManyToMany(mappedBy = "cars",
			cascade = {CascadeType.PERSIST,
					CascadeType.MERGE,
					CascadeType.REMOVE})
	private Set<Owner> owners = new HashSet<Owner>();

}
