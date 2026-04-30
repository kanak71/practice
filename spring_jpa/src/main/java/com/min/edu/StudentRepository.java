package com.min.edu;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.min.edu.entity.StudentEntity;

//TODO 003 StudentEntity에 CRUD를 위한 JpaRepository
@Repository
public interface StudentRepository extends JpaRepository<StudentEntity, Long> {

	
	//TODO 012 email을 통한 DB 조회 하는 JPQL
	// Optinal 객체가 존재하는지 안하는지 확인하는 기능을 가진 클래스
	// Mybatis에서 selectOne 객체/null을 반환 => if
	@Query("SELECT s FROM StudentEntity s WHERE s.email = ?1")
	Optional<StudentEntity> findStudentByEmail(String email);
}
