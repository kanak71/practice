package com.min.edu.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.min.edu.entity.Subject;

public interface SubjectRepository extends JpaRepository<Subject, Long> {

}
