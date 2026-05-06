package com.min.edu.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.min.edu.entity.Owner;

//TODO 004 Owner entity의 Repository 작성
public interface OwnerRepository extends JpaRepository<Owner, Long> {

}
