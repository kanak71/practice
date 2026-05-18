package com.min.edu;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.min.edu.repository.CarRespository;
import com.min.edu.vo.Car;

@SpringBootTest
class SpringbootFinalApplicationTests {

	@Autowired
	private CarRespository carRespository;
	
//	@Test
//	void carRespository_Tsest() {
//		List<Car> find = carRespository.findByBrand("Kia");
//		System.out.println("검색된 Kia Brand의 첫번째조회값 :" + find.get(0).getModel());
//		assertEquals(1, find.size());
//	}

}







