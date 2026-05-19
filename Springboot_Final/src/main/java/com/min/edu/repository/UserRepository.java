package com.min.edu.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.min.edu.vo.User;

public interface UserRepository extends JpaRepository<User, Long> {
	
	//인증 프로세스 진행시 user를 찾는 사용되는 쿼리 메소드 작성
	//Optional 타입으로 작성해서 null 예외를 방지할 수 있도록 작성
	Optional<User> findByUsername(String username);
	
	

}
