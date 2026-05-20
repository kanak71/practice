package com.min.edu.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.min.edu.vo.Car;

//https://docs.spring.io/spring-data/jpa/reference/4.1/jpa/query-methods.html?utm_source
@RepositoryRestResource(path = "vehicles")
public interface CarRespository extends JpaRepository<Car, Long> {

	
	//JPA Query method : 코드(문법)로만들면 자동으로 hibernate가 SQL문으로 변경
	//findBy , And/Or , OrderBy , @Query
	
	// 1. findBy 컬럼
	//  1) brand를 통한 자동차 검색
//	List<Car> findByBrand(String brand);
	//  2) color를 통한 자동차 검색
//	List<Car> findByColor(String color);
	//  3) productYear 를 통한 검색
	List<Car> findByProductYear(String productYear);
	
	
	
	//2. And 와 Or를 통한 여러 컬럼 검색
	//  1) brand와 model을 통한 자동차 검색
	List<Car> findByBrandAndModel(String brand, String model);
	//  2) brand나 model을 통한 자동차 검색
	List<Car> findByBrandOrModel(String brand, String model);
	
	
	
	//3. OrderBy 를 통한 정렬
	//  1) brand로 검색하여 priductYear로 오름차순 정렬
	List<Car> findByBrandOrderByProductYearAsc(String brand);
	
	//4. Query Annotation 을 통한 쿼리문 작성
	//  1) 바인딩(쿼리문에 값을 입력) ?1 와 같이 index를 통한 입력 받는다
	@Query("SELECT c FROM Car c WHERE c.brand = ?1")
	List<Car> findByBrandQuery(String findByBrand);
	//  2) LIKE 문
	@Query("SELECT c FROM Car c WHERE c.brand LIKE %?1")
	List<Car> findByBrandEndsWith(String brand);
	
	
	@Query("SELECT c FROM Car c WHERE c.brand = :brand")
	List<Car> findByBrand(@Param("brand") String brand);
	
	@Query("SELECT c FROM Car c WHERE c.color = :color")
	List<Car> findByColor(@Param("color") String color);
	
	
	
}













