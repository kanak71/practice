package com.min.edu.ctrl;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.min.edu.repository.CarRespository;
import com.min.edu.vo.Car;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequiredArgsConstructor
public class CarController {

	private final CarRespository carRespository;
	
	// Iterable은 Car객체를 여러개 저장. 반복할 수 있는 타입
	// 반복만 가능
	// index를 통한 접근, size(), get(0) 이 불가능하다
	@RequestMapping("/cars")
	public Iterable<Car> getCars(){
		log.info("전체 자동차(car) 테이블 조회");
		return carRespository.findAll(); //JPA 기본 메소드 findAll()
	}
	 
}







