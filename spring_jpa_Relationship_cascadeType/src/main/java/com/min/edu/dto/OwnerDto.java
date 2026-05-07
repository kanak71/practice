package com.min.edu.dto;

import java.util.Set;

import com.min.edu.entity.Car;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

//TODO 008 Lazy Loading 처리를 위한 조회된 객체를 담는 DTO 객체
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OwnerDto {

	private Long id;
	private String name;
	private Set<Car> cars;
}
