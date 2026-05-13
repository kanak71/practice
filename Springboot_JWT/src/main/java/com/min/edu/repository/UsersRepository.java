package com.min.edu.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.min.edu.model.Users;

public interface UsersRepository extends JpaRepository<Users, Long> {
	
	//query name, username으로 컬럼을 조회하는 query method
	Users findByUsername(String username);

}
