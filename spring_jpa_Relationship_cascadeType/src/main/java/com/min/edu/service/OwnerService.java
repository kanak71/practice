package com.min.edu.service;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.min.edu.dto.OwnerDto;
import com.min.edu.entity.Owner;
import com.min.edu.repository.OwnerRepository;

import jakarta.transaction.Transactional;

//TODO 009 프로덕션 DTO인 OwnerDto에 조회 값을 변환하여 생성

@Service
public class OwnerService {
	
	@Autowired
	private OwnerRepository ownerRepository;
	
	//TODO 010-01 @Transactional을 통해서 강제 초기화를 통해서 getCars를 초기화
	@Transactional
	public OwnerDto getOwnerWithCar_Transactional(Long ownerId) {
		Owner owner = ownerRepository.findById(ownerId)
					.orElseThrow(()-> new IllegalStateException("소유자가 존재하지 않습니다"));
		System.out.println("소유자의 정보 ID:" + owner.getId());
		System.out.println("소유자의 정보 NAME:" + owner.getName());
		
		//Lazy Loading 컬렉션을 강제로 초기화
		owner.getCars().size();
		
		return new OwnerDto(owner.getId(), owner.getName(), owner.getCars());
	}
	
	//TODO 011-02 join fetch를 사용한 Service
	public OwnerDto getOwnerWithCars_join_fetch(Long ownerId) {
		Owner owner = ownerRepository.findByIdWithVCars(ownerId)
						.orElseThrow(()-> new IllegalStateException("소유자가 존재하지 않습니다"));
		return new OwnerDto(owner.getId(), owner.getName(), owner.getCars());
	}
	
	//TODO 013-01 Hibernate.initialize()를 통해서 명시적으로 초기화
	//			유의사항 : Hibernate에 너무 의존적이기 때문에 join fetch, JPQL
	public OwnerDto getOwnerWithCars_initialize(Long ownerId) {
		Owner owner = ownerRepository.findById(ownerId)
						.orElseThrow(()-> new IllegalStateException("소유자가 존재하지 않습니다"));
		
		//Lazy Loading 컬렉션을 초기화
		Hibernate.initialize(owner.getCars());
		
		return new OwnerDto(owner.getId(), owner.getName(), owner.getCars());
		
	}
}
