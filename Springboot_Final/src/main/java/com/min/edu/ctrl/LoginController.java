package com.min.edu.ctrl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.min.edu.service.JwtService;
import com.min.edu.vo.AccountCredentials;

/*
 * 로그인 처리를 위한 Controller
 * 로그인 성공시 서명된 JWT를 생성하는데 필요한 JwtService를 주입하여 사용한다
 */
@RestController
public class LoginController {
	
	@Autowired
	private JwtService jwtService;
	
	// SecurityConfig에 @Bean으로 생성 AuthenticationManager를 주입
	// Jwt를 만들어서 인증처리를 위해(AuthenticationManager)-> SpringContext 넣어줌 -> Principal 를 사용
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseEntity<?> getToken(@RequestBody AccountCredentials credentials){
		// 토큰을 생성하고 응답의 Authorization 헤더로 보냄
		UsernamePasswordAuthenticationToken creds = 
				new UsernamePasswordAuthenticationToken(credentials.getUsername(), credentials.getPassword());
		Authentication auth = authenticationManager.authenticate(creds);
		
		// 토큰을 생성한
		String jwts = jwtService.generateToken(auth.getName());//인증된 username을 넣어 준다
		
		// 토큰을 응답(Authorization 헤더로 보낸다. 값은 Bearer 시작한다)
		return ResponseEntity.ok()
					.header(HttpHeaders.AUTHORIZATION, "Bearer "+jwts)
					.header(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS, "Authorization")
					.build();
	}
	
}









