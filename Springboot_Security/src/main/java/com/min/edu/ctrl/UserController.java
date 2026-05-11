package com.min.edu.ctrl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.min.edu.service.IUserService;

import lombok.RequiredArgsConstructor;

//TODO 011 회원관리 요청 Controller
@RestController
@RequiredArgsConstructor
public class UserController {
	
	private final IUserService service;
	
	//로그인 성공후 호출되는 요청
	//spring Security의 로그인 후 정보는 AuthenticationRrinciple에 담겨 있다/
	//로그인 정보를 받아서 AutehnticationPrincipal에서는 username, Auth만 담겨 있다
	//필요한 경우 AuthenticationPrincipal을 오버라이드에서 값을 담아 주면 더 많은 값을 화면에 전송 할 수 있다
	@GetMapping("/loginOk")
	public ResponseEntity<Map<String, Object>> loginOk(@AuthenticationPrincipal UserDetails user){
		Map<String, Object> login = new HashMap<String, Object>();
		//사용자 이름과 권한을 반환하는 객체
		login.put("username", user.getUsername());
		login.put("auth", user.getAuthorities());
		
		return ResponseEntity.ok(login);
	}

}
