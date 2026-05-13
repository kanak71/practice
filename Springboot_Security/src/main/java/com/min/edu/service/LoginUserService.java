package com.min.edu.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.min.edu.config.CustomUserDetail;
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
		//전) 기존의 org.springframework.security.web.userdetails.User를 사용하는 코드
//		UserEntity userEntity = repository.findById(id)
//							.orElseThrow(()-> new UsernameNotFoundException("사용자를 찾을 수 없습니다 : " + id));
//		
//		//User.builder()에 값을 넣어주면 자동으로 Spring Security가 암호화되어있는 계정/비밀번호/ROLE을 알아서 판단해 준다
//		if(userEntity != null) {
//			return User.builder()
//						.username(userEntity.getId())
//						.password(userEntity.getPassword())
//						.roles(userEntity.getRole()) 	//"USER"로 접두사를 필요로 하지 않을 때 사용가능
//						.build();
//		}else {
//			throw new UsernameNotFoundException("조회된 회원이 없습니다");
//		}
		
		//후) 오버라이딩한 CustomUserDetail을 통한 처리
		UserEntity user = repository.findById(id)
									.orElseThrow(()-> new UsernameNotFoundException("사용자를 찾을 수 없습니다"+id));
		System.out.println("검색된 사용자 " + user);
		
		//검색된 UserEntity를 기반으로 CustomUserDetail에서 반환하는 JSON을 만들어 준다
		return new CustomUserDetail(user);
	}

}
