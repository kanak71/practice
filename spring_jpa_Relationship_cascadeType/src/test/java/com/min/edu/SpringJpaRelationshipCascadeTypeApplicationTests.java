package com.min.edu;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import com.min.edu.dto.OwnerDto;
import com.min.edu.entity.Car;
import com.min.edu.entity.Owner;
import com.min.edu.repository.CarRepository;
import com.min.edu.repository.OwnerRepository;
import com.min.edu.service.OwnerService;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@SpringBootTest
class SpringJpaRelationshipCascadeTypeApplicationTests {
	
	@Autowired
	private CarRepository carRepository;
	
	@Autowired
	private OwnerRepository ownerRepository;
	
	@Autowired
	private OwnerService ownerService;

	/*
	 * PERSIST는?
	 * 부모 엔터티를 저장할 때 자식 엔터티로 자동으로 저장되도록 하는 설정
	 * 새로 생성된 객체를 서로 연결 할 때의 설정
	 * TODO 005
	 */
	@Test
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
//		Owner owner = ownerRepository.findById(2L).get();
//		Set<Car> cars = owner.getCars();
//		assertNotEquals(0, cars.size());
		
		
		//TODO 010-02 프로덕션 DTO를 통해 DTO 객체로 변환하여 반환하는 Service를 만들어서 처리
//		OwnerDto ownerDto = ownerService.getOwnerWithCar_Transactional(2L);
//		System.out.println(ownerDto.getCars());
//		assertNotEquals(0, ownerDto.getCars().size());
		
		//TODO 011-03 join fetch를 통한 데이터 로드
//		OwnerDto ownerDto = ownerService.getOwnerWithCar_Transactional(2L);
//		assertNotEquals(0, ownerDto.getCars().size());
		
		//TODO 012-02 EntityGraph를 통한 Lazy Loading을 즉시 로딩시킴
//		Owner owner = ownerRepository.findById(2L).get();
//		System.out.println(owner.getCars());
//		assertNotEquals(0, owner.getCars().size());
		
		//TODO 013-02 Hibernate.initialize() 로 명시적으로 초기화
		OwnerDto ownerDto = ownerService.getOwnerWithCars_initialize(2L);
		assertNotEquals(0, ownerDto.getCars().size());
		
	}
	
	//TODO 014 CascadeType.MERGE는 부모 엔터티를 병합(수정) 할 때 자식 엔터티도 병합되도록 하기 위해서 사용
	
	@PersistenceContext	//JPA 표준방법
	private EntityManager em;
	
//	@Test
	public void merge_OwnerAndCar_test() {
		//owner와 car 모두 findById로 조회, 이미 영속성 컨텍스트에 관리중인 상태
		//따라서 MERGE를 필요하지 않는다. 
		//시나리오 : 사용자에게 새로운 자동차 정보를 입력한다
		Owner owner = ownerRepository.findById(2L)
					.orElseThrow(()-> new IllegalStateException("소유자가 존재하지 않습니다"));
		Car car = carRepository.findById(1L)
					.orElseThrow(()-> new IllegalStateException("자동차가 존재하지 않습니다"));
		
		//CasecadeType.Merge가 의도대로 동작하게 하거나, 문제가 없도록 보장하면
		owner.getCars().add(car);
		ownerRepository.save(owner);
		
		//mappedBy(자식 선언 - car Entity)에 의해서 Car는 자식 엔터티가 되기 때문에 읽기 전용이다. 따라서 
		//아래 코드는 실행되지 않는다
		//"즉, Owner Entity에 의해서만 데이터베이스 관계가 업데이트  된다"
//		car.getOwners().add(owner);
//		carRepository.save(car);
		
		em.flush(); 	//변경사항을 강제적으로 반영
		
	}
	
	//TODO 015 CasecadeType.REMOVE 부모를 삭제하면 자식도 삭제된다
	//		현재 car 1이 owner 2와 연결되어 있다
	//		casecade.Tyep.REMOVE를 실행시키면 *** 삭제 연쇄반응이 일어난다(Casecade Cycle) ** 가 발생하여 모두 삭제된다
	//		즉. CasecadeType.REMOVE를 사용하지 않아야지만 각 Car와 Owner를 단독으로 사용할 수 있다
	@Test
	public void cascade_remove() {
		//1. 삭제 실행
		Owner owner = ownerRepository.findById(2L)
						.orElseThrow(()-> new IllegalStateException("소유자가 존재하지 않습니다"));
		ownerRepository.delete(owner);
		
		
		//2. 강제로 DB 반영(SQL실행)
		ownerRepository.flush();
		//3. 실제로 삭제되었는지 Assert로 확인
		boolean exists = ownerRepository.existsById(2L);
		//*** CasecadeType.REMOVE 설정에 의해서 owner가 삭제되면 연관된 car도 같이 삭제된다
	}
	
	//TODO 016 연관 관계를 해제하고 Owner와 Owner_car의 테이블의 값을 삭제
	@Test
	@Transactional
	@Rollback(false) //JUnit에서는 모두 다 Rollback으로 동작되기 때문에 작성, Service/Repository는 안해도 됨
	public void cascade_REMOVE_without_casecade() {
		//1. 삭제 대상을 조회
		Owner owner = ownerRepository.findById(2L)
						.orElseThrow(()-> new IllegalStateException("소유자 정보 없음"));
		
		//2. Owner의 관계인 Car 객체에서 Owner 제거
		for(Car car : owner.getCars()) {
			car.getOwners().remove(owner);	//owner_car관계 테이블에 틀정 행만을 삭제
		}
		
		//3. 관계가 끊어진 Owner 객체를 저장
		ownerRepository.save(owner);	//변경된 관계를 DB에 저장
		
		//4. Owner 객체를 삭제
		ownerRepository.delete(owner);	//Owner 삭제
		
		//5. 삭제 후 Car 객체에서 Onwer 객체가 제대로 삭제되었는지 확인하는 코드
		for(Car car : owner.getCars()) {
			assertFalse(car.getOwners().contains(owner));	//Owner가 없음을 확인
		}
	}
	
	//TODO 017 owner 및 car는 값을 유지하고 관계 테이블은 owner_car의 연관관계만 삭제
	@Test
	@Transactional
	@Rollback(false)
	public void removeOwnerCar_Relationship() {
		//1. 삭제대상
		Owner owner = ownerRepository.findById(2L)
				.orElseThrow(()-> new IllegalStateException("소유자 정보 없음"));
		
		//2. Owner에서 관계된 Car 객체에서 제거
		for(Car car : owner.getCars()) {
			car.getOwners().remove(owner);
		}
		
		//3. 관계 끊기
		owner.getCars().clear();	//owner_car에서 목록에서 모든 관계 제거
		
		//4. DB에 반영
		ownerRepository.save(owner);
	}
	

}







