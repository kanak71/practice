package com.min.edu.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.min.edu.domain.UserEntity;

//TODO 002 UserEntity의 JPA 기능 인터페이스
@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
	
	//TODO JPQL을 통한 ID를 통한 조회
	
	@Query("SELECT s FROM UserEntity s WHERE s.id = :id")
	public Optional<UserEntity> findById(@Param("id") String id);

}
