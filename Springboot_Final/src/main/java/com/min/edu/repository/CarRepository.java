package com.min.edu.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.min.edu.vo.Car;

public interface CarRepository extends CrudRepository<Car, Long> {

	//JPA Query method : 코드로 만들면 자동으로 hibernate가 SQL문으로 변경
	//findBy, And/Orm OrderBy, @Query
	
	//1. findBy 컬럼
	// 1) Brand를 통한 자동차 검색
	List<Car> findByBrand(String brand);
	// 2) Color를 통한 자동차 검색
	List<Car> findByColor(String color);
	// 3) productYear 를 통한 검색
	List<Car> findByProductYear(String productYear);
	
	//2. And와 Or를 통한 여러 컬럼 검색
	// 1) brand와 model를 통한 자동차 검색
	List<Car> findByBrandAndModel(String brand, String model);
	// 2) brand나 model을 통한 자동차 검색
	List<Car> findByBrandOrModel(String brand, String model);
	
	//3. OrderBy를 통한 정렬
	// 1) brand로 검색하여 productYear로 오름차순 정렬
	List<Car> findByBrandOrderByProductYearAsc(String brand);
	
	//4. Query Annotation을 통한 쿼리문 작성
	// 1) 바인딩 (쿼리문에 값을 입력) ?1 와 같이 index를 통한 입력 받는다
	@Query("SELECT c FROM Car c WHERE c.brand = ?1")
	List<Car> findByBrandQuery(String findByBrand);
	//2) LIKE문
	@Query("SELECT c FROM Car c WHERE c.brand LIKE %?1")
	List<Car> findByBrandEndsWith(String brand);
	
	

}












