package com.min.edu.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.min.edu.domain.UserEntity;
import com.min.edu.repository.UserRepository;

import lombok.RequiredArgsConstructor;

//TODO 004 Service의 기능을 작성하는 ServiceImpl JPA Repositoy를 실행
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {

	private final UserRepository repository;
	
	@Override
	public Optional<UserEntity> getUserInfo(String id) {
		return repository.findById(id);
	}

	@Override
	public UserEntity register(UserEntity user) {
		return repository.save(user);
	}
	
	//TODO 010 회원 전체 조회 기능 작성
	@Override
	public List<UserEntity> getAllUser() {
		return repository.findAll();
	}

}
