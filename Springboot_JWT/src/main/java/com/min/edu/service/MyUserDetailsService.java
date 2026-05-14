package com.min.edu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.min.edu.model.Users;
import com.min.edu.repository.UsersRepository;

//UserDetailService를 오버라이드 하여 security 로그인 시 database의 정보를 확인하는 서비스
@Service
public class MyUserDetailsService implements UserDetailsService {

	@Autowired
	private UsersRepository repository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		//Optional<> : 조회된 타입을 확인해서 진행하겠다
		//				그냥 값을 호출하고 싶다 .get
		//				판단을 통해서 null인 경우 어떠한 작업을 하겠다 orElse, orElseThrow(()-> new RuntimeException)
		Users user = repository.findByUsername(username);
		
		if(user == null) {
			System.out.println("사용자가 존재하지 않습니다");
			throw new UsernameNotFoundException("사용자가 존재하지 않습니다");
		} else {
			System.out.println("사용자를 찾았습니다");
		}
		//인증 -> 인가 -> UserDetails에 정보를 담고 -> Authentication 객체가
		//요청 -> Security Filter -> AuthenticationManager -> AuthenticationProvider -> UserDetailService -> DB조회 -> 비교 -> UserDetail -> @AuthenticationPrincipal
		//Builder Pattern 통해서 값을 담아준다. User > UserDetails
		return User.builder()
				.username(user.getUsername()+"")
				.password(user.getPassword())	//필요한 raw(화면에서 받은 값) = DB에 있는 값 비교
				.roles("USER")	//"USER"로 접두사가 필요로 하지 않을 시 사용 가능
				.build();
	}

}
