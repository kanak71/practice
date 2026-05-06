package com.min.edu.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.min.edu.entity.Car;

//TODO 003 Car Entity의 JpaRepository
public interface CarRepository extends JpaRepository<Car, Long>{

}
