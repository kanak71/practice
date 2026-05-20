package com.min.edu.repository;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.min.edu.vo.Owner;

public interface OwnerRepsitory extends JpaRepository<Owner, Long> {

	@Query("SELECT o FROM Owner o JOIN FETCH o.cars")
	List<Owner> findAllwithCars();
	
	
	// 속성명인데, Entity에 있는 붙여 올 값
	@EntityGraph(attributePaths = "cars")
	List<Owner> findAll();
}





