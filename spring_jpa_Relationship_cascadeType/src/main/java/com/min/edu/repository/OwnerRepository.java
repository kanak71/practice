package com.min.edu.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.min.edu.entity.Owner;

//TODO 004 Owner entity의 Repository 작성
public interface OwnerRepository extends JpaRepository<Owner, Long> {
	
	//TODO 011-01 join fetch를 사용하여 데이터를 한번에 로드 @Query를 통해서 작성
	//		SQL문에 ORM의 객체를 사용한다
	//		유의사항 : 오타 객체를 명확하게 명명법을 사용해야한다, 쿼리는 대문자
	
	@Query("SELECT o FROM Owner o LEFT JOIN FETCH o.cars WHERE o.id = :ownerId")
	Optional<Owner> findByIdWithVCars(Long ownerId);
	
	//TODO 012-01 Entity Graph를 통한 즉시로딩 선언
	//		EntityGraph는 연관 엔터티를 어떠한 방식으로 조회(fetch)할지는 지정한다
	@EntityGraph(attributePaths = "cars")
	Optional<Owner> findById(Long ownerId);
	

}
