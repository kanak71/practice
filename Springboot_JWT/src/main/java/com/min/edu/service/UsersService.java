package com.min.edu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.min.edu.model.Users;
import com.min.edu.repository.UsersRepository;

@Service
public class UsersService {

	@Autowired
	private UsersRepository repository;

	//정보를 입력할 때 사용자의 password는 BcyptPasswordEncoder 입력
	private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
	
	public Users register(Users user) {
		user.setPassword(encoder.encode(user.getPassword()));
		Users outUser = repository.save(user);
		return outUser;
	}
	
}



