package com.min.edu.service;

import java.util.List;
import java.util.Optional;

import com.min.edu.domain.UserEntity;

//TODO 003 기능 정의를 위한 Service Interface
public interface IUserService {
	
	//로그인(ID)를 통한 회원 조회
	public Optional<UserEntity> getUserInfo(String id);
	
	
	//회원가입을 위한 정보 입력(id, password)
	//JPA는 객체를 통한 save를 실행
	public UserEntity register(UserEntity user);
	
	//TODO 009 회원전체조회
	public List<UserEntity> getAllUser();
	
	
}
