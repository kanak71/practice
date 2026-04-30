package com.min.edu.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.min.edu.entity.Teacher;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {

}
