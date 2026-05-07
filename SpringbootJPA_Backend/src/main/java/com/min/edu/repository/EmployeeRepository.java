package com.min.edu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.min.edu.model.Employee;



//TODO 002 Employee CRUD를 위한 Repository 작성
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

}
