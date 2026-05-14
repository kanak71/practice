package com.min.edu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.min.edu.model.Users;
import com.min.edu.repository.UsersRepository;

@Service
public class UsersService {

	@Autowired
	private UsersRepository repository;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JWTService jwtService;

	//정보를 입력할 때 사용자의 password는 BcyptPasswordEncoder 입력
	private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
	
	public Users register(Users user) {
		user.setPassword(encoder.encode(user.getPassword()));
		Users outUser = repository.save(user);
		return outUser;
	}
	
	//TODO 005 Controller에서 전달받은 JSON값을 JPA에 검색 로그인 처리
	//authenticationManager를 통해서 요청받은 username과 password를 DB에서 인증을 확인한다
	public String verify(Users user) {
		
		Authentication authentication = 
				authenticationManager.authenticate(
						new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
						);
		
		//인증을 확인
		if(authentication.isAuthenticated()) {
			//TODO 008 token으로 반환하는 코드로 변경
//			return "seccess";
			return jwtService.generateToken(user.getUsername());
		}
		return "fail";
	}
	
	
}



