package com.min.edu.ctrl;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.min.edu.repository.CarRepository;
import com.min.edu.vo.Car;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequiredArgsConstructor
public class CarController {
	//DI(주입) : Spring이 사용객체(Bean)을 다른 Bean에서 사용하는 것 => ref를 통해서 가져오게 된다
	
	private final CarRepository carRepository;
	
	//Iterable은 Car객체를여러개 저장. 반복할 수 있는 타입
	//반복만 가능
	//index를 통한 접근, size(), get(0)이 불가능하다
	@RequestMapping("/cars")
	public Iterable<Car> getCars(){
		log.info("전체 자동차(car)테이블 조회");
		return carRepository.findAll();	//JPA 기본 메소드 findAll()
	}

	
	
	

}
