package com.min.edu.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.min.edu.repository.UserRepository;
import com.min.edu.vo.User;

public class UserDetailServiceImpl implements UserDetailsService {
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> user = userRepository.findByUsername(username);
		
		UserBuilder builder = null;
		
		if(user.isPresent()) {	//Optional 타입을 사용하 ㄴ이유는 isPresent()가 null을 확인해준다
			User currentUser = user.get();
			builder = org.springframework.security.core.userdetails.User.withUsername(username);
			builder.password(currentUser.getPassword());
			builder.roles(currentUser.getRole());
			
		}else {
			throw new UsernameNotFoundException("회원이 존재하지 않습니다");
		}
		
		return null;
	}

}
