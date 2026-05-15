package com.min.edu.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.min.edu.entity.User;


public interface UserRepository extends JpaRepository<User, Long> {
	
	//조회는 ID(기본식별자)가 아닌 보조식별자(email - unique)를 통해서 조회
	//쿼리 메소드
	Optional<User> findByEmail(String email);
	

}
