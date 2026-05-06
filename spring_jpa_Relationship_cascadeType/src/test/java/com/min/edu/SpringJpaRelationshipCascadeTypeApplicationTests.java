package com.min.edu;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.min.edu.entity.Car;
import com.min.edu.entity.Owner;
import com.min.edu.repository.CarRepository;
import com.min.edu.repository.OwnerRepository;

@SpringBootTest
class SpringJpaRelationshipCascadeTypeApplicationTests {
	
	@Autowired
	private CarRepository carRepository;
	
	@Autowired
	private OwnerRepository ownerRepository;

	/*
	 * PERSIST는?
	 * 부모 엔터티를 저장할 때 자식 엔터티로 자동으로 저장되도록 하는 설정
	 * 새로 생성된 객체를 서로 연결 할 때의 설정
	 * TODO 005
	 */
//	@Test
	public void cascadeType_PERSIST() {
		
		Owner owner = new Owner();
		owner.setName("Owner_1"); 
		
		Car car = new Car();
		car.setName("car_1");
		
		//연결
		owner.getCars().add(car);	//owner에 Car객체를 연결
		car.getOwners().add(owner);	//car에 Owner객체를 연결
		
		//저장 OwnerRepository를 통해서 owner만을 저장했아면 Car로 자동 저장된다
		Owner obj = ownerRepository.save(owner);
		assertNotNull(obj);
	}
	
	//TODO 007 Owner를 조회시 Car를 호출 했을 경우 @ManyToMany의 Lazy Loading에 의해서 호출이 되지 않는다
	
	@Test
	public void Owner_Lazy_Loading() {
		//TODO 007
		//		-No session 예외 발생
		Owner owner = ownerRepository.findById(2L).get();
		Set<Car> cars = owner.getCars();
		assertNotEquals(0, cars.size());
	}

}
