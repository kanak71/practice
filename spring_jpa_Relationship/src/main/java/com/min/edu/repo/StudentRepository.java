package com.min.edu.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.min.edu.entity.Student;

public interface StudentRepository extends JpaRepository<Student, Long> {

}
