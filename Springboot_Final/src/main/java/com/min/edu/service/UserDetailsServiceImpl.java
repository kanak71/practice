package com.min.edu.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.min.edu.repository.UserRepository;
import com.min.edu.vo.User;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// 전달받은 username => Spring Config에서 loginForm에서 확인 => DB 조회 
		// 1) 비밀번호 없이 객체를 조회
		Optional<User> user = userRepository.findByUsername(username);
		
		//2) security 에서 사용자를 판단하기 위한 UserBuilder 객체를 생성
		UserBuilder builder = null;
		
		if(user.isPresent()) { // Optional 타입을 사용한 이유는 isPresent()가 null을 확인해 준다
			//3) 사용자가 있다면 Optional 타입에서 원래 조회된 Entity 타입을 변경
			User currentUser = user.get();
			//4) withUsername() 이라하는 함수는 반환값으로 userBuilder(객체)를 반환한다.
			//   기능으로 UserBuilder 객체 내부에 있는 변수 username에 withUsername(username)의 매개변수로 들어 값을 할당
			builder = org.springframework.security.core.userdetails.User.withUsername(username);
			//5) DB에서 검색된 값을 password 할당
			builder.password(currentUser.getPassword());
			//6) DB에서 검색 role을 할당
			builder.roles(currentUser.getRole());
		}else {
			throw new UsernameNotFoundException("회원이 존재하지 않습니다");
		}
		
		return builder.build();
	}

}





