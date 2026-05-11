package com.min.edu.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.min.edu.domain.UserEntity;
import com.min.edu.repository.UserRepository;

import lombok.RequiredArgsConstructor;

//TODO 013 DB조회 로그인
@Component
@RequiredArgsConstructor
public class LoginUserService implements UserDetailsService {
	
	private final UserRepository repository;

	@Override
	public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
		UserEntity userEntity = repository.findById(id)
							.orElseThrow(()-> new UsernameNotFoundException("사용자를 찾을 수 없습니다 : " + id));
		
		//User.builder()에 값을 넣어주면 자동으로 Spring Security가 암호화되어있는 계정/비밀번호/ROLE을 알아서 판단해 준다
		if(userEntity != null) {
			return User.builder()
						.username(userEntity.getId())
						.password(userEntity.getPassword())
						.roles(userEntity.getRole()) 	//"USER"로 접두사를 필요로 하지 않을 때 사용가능
						.build();
		}else {
			throw new UsernameNotFoundException("조회된 회원이 없습니다");
		}
	}

}
