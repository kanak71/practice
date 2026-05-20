package com.min.edu;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.min.edu.repository.CarRespository;
import com.min.edu.repository.OwnerRepsitory;
import com.min.edu.vo.Car;
import com.min.edu.vo.Owner;

@SpringBootTest
class CarRepositoryTests {

	@Autowired
	private CarRespository carRespository;
	
	@Autowired
	private OwnerRepsitory ownerRepsitory;
	
	
	/*
	 * Test를 위한 자동차 정보를 등록
	 * @BeforeAll을 통해서 클래스 단위에서 한번만 실행 되도록 한다
	 */
//	@BeforeAll
//	@DisplayName("JUnit Test를 위한 Sample Data 입력")
//	public static void setUp(@Autowired CarRespository carRepository, @Autowired OwnerRepsitory ownerRepository) {
//		Owner owner = new Owner("Testuser", "GoodYear");
//		ownerRepository.save(owner);
//		carRepository.save( new Car("Hyundai", "Sonata","Black","34나5678","2023", 32000000 , owner));
//		carRepository.save( new Car("Kia", "Ray","White", "56다7890", "2022",  18000000 , owner));
//		carRepository.save( new Car("Kia", "Model Y","White", "56다1111", "2024",  20000000 , owner));
//		carRepository.save( new Car("Tesla", "Model X","Red", "56다1123", "2025",  21000000 , owner));
//		carRepository.save( new Car("Tesla", "Model Z","Black", "56다1567", "2026",  22000000 , owner));
//	}

	/*
	 * Car의 Entity 값 중 Brand를 통해서 자동차 검색
	 */
	@Test
	@DisplayName("Brand를 통한 자동차 검색")
	public void findByBrand() {
		//Kia 브렌드로 자동차 검색
		List<Car> carList = carRespository.findByBrand("Kia");
		
		// 결과가 null이 아닌지 확인
		assertNotNull(carList,"자동차 목록이 null이 어서는 안됩니다");
		// 최소 1개의 자동차가 반환되었는지 확인
		assertFalse(carList.isEmpty(),"Kia 브랜드 자동차가 하나도 반환되지 않았습니다");
		
		//반환된 자동차 목록 출력(옵션)
		carList.forEach(car -> {
			System.out.println("검색된 자동차 : " + car.getModel());
			System.out.println("해당 소유자 : "+ car.getOwner());
			System.out.println("--".repeat(20));
		});
	} // findByBrand 끝
	

	@Test
	@DisplayName("Color를 통한 자동차 검색")
	public void testFindByColor() {
		List<Car> carList = carRespository.findByColor("Red");
		assertNotNull(carList);
		assertTrue(carList.size() >0);
		carList.forEach(car -> assertEquals("Red", car.getColor()));
	}
	
	@Test
	@DisplayName("ProducYear를 통한 자동차 검색")
	public void testFindByProductYear() {
		List<Car> carList = carRespository.findByProductYear("2023");
		assertNotNull(carList);
		assertTrue(carList.size()>0);
		carList.forEach(car -> assertEquals("2023",  car.getProductYear()));
	}
	
	@Test
	@DisplayName("Brand와 Model을 통한 자동차 검색")
	public void testFindByBrandAndModel() {
		List<Car> carList = carRespository.findByBrandAndModel("Kia", "Model Y");
		assertNotNull(carList);
		assertTrue(carList.size()>0);
		assertEquals("Kia", carList.get(0).getBrand());
		assertEquals("Model Y", carList.get(0).getModel());
	}
	
	@Test
	@DisplayName("Brand로 검색하고 ProductYear를 오름차순으로 정렬")
	public void testFindByBrandOrderByProductYearAsc() {
		
	}
	
	@Test
	@DisplayName("Like 문을 사용하여 Brand명으로 끝나느 자동차 검색")
	public void testFindByBrandEndWith() {
		
	}
	
	//--------------------------------------------------------------------------
	// Owner의 FetchType이 Lazy가 아니면 연관되어 있기 때문에 삭제가 되지 않는다
	// 따라서 반드시 FetchType을 확인해야 한다
//	@ParameterizedTest
//	@ValueSource(longs = {2})
//	@DisplayName("Car ID를 통한 테스트")
//	public void testDeleteCarId(Long carId) {
//		//ID가 1인 car가 데이테베이스에 존재 하는지 확인
//		Car car = carRespository.findById(carId).orElse(null);
//		assertNotNull(car,"Car를 삭제 하기 위한 정보가 없음");
//		
//		//ID가 1인 car를 삭제
//		carRespository.deleteById(carId); // deleteById(Long)를 통해서 식별자(id)를 통해서 삭제
////		carRespository.delete(car);		  // delete(Entity)를 통해서 객체를 통해서 삭제
//		
//		// 삭제후 확인하는 방법
//		assertFalse(carRespository.findById(carId).isPresent(), "Car 정보가 삭제 되어 존재하지 않음");
//	}
	
	
	@Test
	@DisplayName("Car 정보 입력 테스트")
	public void testCarInsert() {
		//Owner 객체를 생성하여 Car 객체를 생성
		Owner owner = ownerRepsitory.findById(1L).orElseThrow(); // 첫번 onwer를 가져옴
		System.out.println(owner.getFirstname());
		
		//Car 객체 생성
		Car car = new Car("Kia", "Model K", "Orange", "55소5156", "2026", 2000000, owner);
//		
//		//Car 객체를 저장
		Car saveCar = carRespository.save(car);
//	
//		//저장된 데이터가 정상적으로 저장되어있는지 확인
		assertNotNull(saveCar.getId()); // 저장된 후 ID가 null이 아니어야 함
		assertEquals("Kia", saveCar.getBrand());
		assertEquals("Model K", saveCar.getModel());
		assertEquals("Orange", saveCar.getColor());
		assertEquals("55소5156", saveCar.getRegisterNumber());
//		
		assertNotNull(saveCar.getOwner());
		assertEquals("Marry", saveCar.getOwner().getFirstname());
		
	}
	
	//--------------------------------------------------------------------------------
	// 3번 은 owner 2 
	@ParameterizedTest
	@ValueSource(longs = {3})
	@DisplayName("Car 정보의 소유자 변경")
	public void testUpdateCarOwner(Long carId) { // 변경할 자동차의 ID
		Long newOwnerId = 1L; // 새로운 소유자 ID
		
		//1 주어진 carId를 통해서 자동차 조회
		Car car = carRespository.findById(carId)
					.orElseThrow(()-> new RuntimeException("자동차가 없습니다"));
		
		//2 주어진 newOwnerId로 새호운 소유자를 조회
		Owner newOwner = ownerRepsitory.findById(newOwnerId)
					.orElseThrow(()->  new RuntimeException("소유자가 없습니다"));
		
		//3 자동자의 소유자를 변경
		car.setOwner(newOwner);
		
		//4.자동자 정보를 저장하면 소유자 변경을 즉시 DB에 반영
		// Dirty Checkin을 통해서 처리하지 않고 명시적으로 save() 메소드를 통해서 명시적으로 flush()를 호출 즉시 DB에 반영
		carRespository.save(car);
		
		//5. 변경된 자동차의 소유자 확인
		Car updateCar = carRespository.findById(carId)
							.orElseThrow(()-> new RuntimeException("차동차 조회 실패") );
		
		//6. 소유자 변경 확인
		assertEquals(newOwnerId, updateCar.getOwner().getOwnerid(), "소유자 변경되었습니다");
	}
}













