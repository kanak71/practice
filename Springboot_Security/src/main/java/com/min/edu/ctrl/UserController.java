package com.min.edu.ctrl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.min.edu.config.CustomUserDetail;
import com.min.edu.domain.UserEntity;
import com.min.edu.service.IUserService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

//TODO 011 회원관리 요청 Controller
@RestController
@RequiredArgsConstructor
public class UserController {
	
	private final IUserService service;
	
	//SpringsecurityConfig 에 작성한 String을 hash값으로 변경해주는 PasswordEncoder 주입
	//전달 받은 문자열은 hash값으로 변경해서 DB에 저장
	private final PasswordEncoder passwordEncoder;
	
	//로그인 성공후 호출되는 요청
	//spring Security의 로그인 후 정보는 AuthenticationRrinciple에 담겨 있다/
	//로그인 정보를 받아서 AutehnticationPrincipal에서는 username, Auth만 담겨 있다
	//필요한 경우 AuthenticationPrincipal을 오버라이드에서 값을 담아 주면 더 많은 값을 화면에 전송 할 수 있다
	
	//후) UserDetails는 CustomUserDetail과 LoginUserService를 통해서 오버라이드된 UserDetail 정보를 가지고 있다
	@GetMapping("/loginOk")
	public ResponseEntity<Map<String, Object>> loginOk(@AuthenticationPrincipal UserDetails user){
		
		//주입된 UserDetails를 원래 형태에 맞는 타입으로 변경 => 추가로 생성한 메소드를 할 수 있다
		//타입에 따라서 사용할 수 있는 메소드가 정해진다. 공개되어 있는 메소드만이 사용이 가능하다
									//다운캐스팅
		CustomUserDetail tmp = (CustomUserDetail)user;
		System.out.println("오버라이드된 UserDetails 정보 확인 \n" + tmp.getUserEntity());
		Map<String, Object> login = new HashMap<String, Object>();
		//사용자 이름과 권한을 반환하는 객체
		login.put("username", user.getUsername());
		login.put("auth", user.getAuthorities());
		login.put("userInfo", tmp.getUserEntity());
		
		return ResponseEntity.ok(login);
	}
	
	@GetMapping("/admin")
	public ResponseEntity<String> getAdminPage(){
		return ResponseEntity.ok("관리자 페이지에 접근하셨습니다");
		
	}
	@GetMapping("/user")
	public ResponseEntity<String> getUserPage(){
		return ResponseEntity.ok("사용자 페이지에 접근하셨습니다");
	}
	
	
	//로그인 상태(Authentication)
	@GetMapping("/checkSession")
	public ResponseEntity<?> checkSession(Authentication authentication, HttpServletRequest request){
		Map<String, Object> response = new HashMap<String, Object>();
		
		//request.getSession(false); 기존세션이 있으면 반환, 없으면 새로 만들지 않음
				//request.getSession(ture);	세션이 없으면 생성 => default이다
				HttpSession session = request.getSession(false);
				
				//세션이 없는 경우 403 응답을 반환
				if(session == null) {
					return ResponseEntity.status(HttpStatus.FORBIDDEN).body("세션이 확인되지 않음");
				}
		
		
		
		//인증 정보가 있고, 익명사용자가 아닌 경우
		//인증 객체가 존재 하는가?(로그인 여부) && 인증이 성공했는가?(true)
		//**** Spring Security는 로그인을 하지않아도 익명의 사용자 객체를 자동 생성한다
		//	AnonymousAuthenticationToken 로그인 안한 사용자
		if(authentication != null && authentication.isAuthenticated()
									&& !(authentication instanceof AnonymousAuthenticationToken)) {
			
			//현재 인증된 사용자의 정보를 가져오기(기본 User > UserDetails)
			UserDetails userDetails = (UserDetails)authentication.getPrincipal();
			
			//1) react에서 로그인 여부의 상태 정보
			response.put("isLoggedIn", true);
			response.put("usesrname", userDetails.getUsername());
			response.put("auth", userDetails.getAuthorities());
			return ResponseEntity.ok(response);	//controller니깐 spring mvc에 jackson-bind 자동으로 JSON

		}
		
		//인증되지 않은 상태
		response.put("isLoggedIn", false);
		return ResponseEntity.ok(response);
	}
	
	@PostMapping("/register")
	public ResponseEntity<String> setRegister(@RequestBody UserEntity entity){
		
		//화면에서 전달받은 비밀번호 값을 암호화 한다
		try {
			String encodePassword = passwordEncoder.encode(entity.getPassword());
			entity.setPassword(encodePassword);
			
			//회원가입 서비스 실행
			service.register(entity);
			return ResponseEntity.ok("회원가입이 성공하였습니다");
		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("이미 존재하는 사용자명 입니다");
		} catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("회원가입에 실패하셨습니다");
		}
	}
	
	@GetMapping("/checkRememberMe")
	public ResponseEntity<Map<String, Object>> checkRememberMe(HttpServletRequest request){
		//쿠키에서 "remember-me-cookie" 확인
		Cookie[] cookies = request.getCookies();
		
		Map<String, Object> response = new HashMap<String, Object>();
		//쿠키확인
		for (Cookie cookie : cookies) {
			System.out.println("쿠키 이름 :" + cookie.getName() + "\t 쿠키값:"+ cookie.getValue());
		}
		
		if(cookies != null) {
			for (Cookie cookie : cookies) {
				//Remember-Me 쿠키가 존재한다면
				//쿠키의 이름은 SpringSecurityConfig의 설정 82번째줄
				if("remember-me-cookie".equals(cookie.getName())) {
					Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
					//인증을 확인
					if(authentication != null && authentication.isAuthenticated()) {
						if(authentication.getPrincipal() instanceof User) {
							CustomUserDetail user = (CustomUserDetail)authentication.getPrincipal();
							//User(USerDetails)객체에서 반환가능한 정보를 포함 => session Strage 담는 값
							response.put("id", user.getUsername());
							response.put("role", user.getAuthorities());
							response.put("userInfo", user.getUserEntity());
							return ResponseEntity.ok(response);
						}
					}
				}
			}
		}//쿠키확인 끝
		System.out.println("쿠키가 확인되지 않음");
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
	}
	
	

}
