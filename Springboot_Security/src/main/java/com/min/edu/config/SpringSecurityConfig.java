package com.min.edu.config;

import java.util.Arrays;

import org.springframework.web.filter.CorsFilter;

import com.min.edu.service.LoginUserService;

import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

//TODO 012 Security Filter Chain을 통해서 요청처리 해준다. 처음 처리
//Spring Security에서 보안 설정 annotation
@Configuration	//이 클래스가 설정파일이다. Spring 실행될때 bean을 등록
@EnableWebSecurity	//Spring Secutiry 기능을 활성화(로그인/권한검사) : Spring Security를 켜라
@EnableMethodSecurity	//메소드 단위 권한제어 기능 @preAuthoize("hasRoll('ADMIN')")
public class SpringSecurityConfig {
	//인증권한
	//로그인처리
	//로그아웃
	
	//rememberMe는 사용자 정보를 가지고 있어야 하기 때문에 UserDetailService를 필요로 한다
	//rememberMe는 쿠키 안에 비밀번호를 직접 저장하지 않는다
	@Autowired
	private LoginUserService userDetailService;
	
	private final CorsConfigurationSource configurationSource;

	SpringSecurityConfig(CorsConfigurationSource configurationSource) {
		this.configurationSource = configurationSource;
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
			.csrf(csrf -> csrf.disable())	//React, VUe... REST API, Postman 테스트
			.cors(cors -> cors
							.configurationSource(configurationSource())
			)
			.authorizeHttpRequests(authorize -> authorize
					.requestMatchers("/loginProcess", "/checkSession","/register", "/checkRememberMe","/sessionCheck").permitAll()	//React에서 요청하는 페이지를 인증없이 접근
//					.anyRequest().permitAll()	//나머지 모든 요청 모두 접근 가능
					/*
					 * "ROLE_ADMIN" or "ROLE_USER" => .hasRole("ADMIN" or "USER") : 자동으로 ROLE_ 접두사로 사용
					 * "ADMIN" or "SYSTEM" => .hasAuthority("ADMIN" or "SYSTEM") : 앞에 접두사가 붙지 않는다
					 */
					.requestMatchers("/admin/**").hasRole("ADMIN")
					.anyRequest().authenticated()	//나머지 모든 요청은 인증 후에만 접근 가능
					)
			//사용자 정의 접근 거부 핸들러 작성 => 403이 발생했을때 JSON으로 정보값을 반환
			.exceptionHandling(exception -> exception
											.accessDeniedHandler(new CustomAccessDeniedHandler()))
			
			.formLogin(login -> login	//React로 구성된 UI구성 때문에 첫페이지 요청이 없어도 된다
					.loginProcessingUrl("/loginProcess")
					.usernameParameter("username")	//react에서 formData로 전달하는 JSON의 key값
					.passwordParameter("password")	//react에서 formData로 전달하는 JSON의 key값
					.defaultSuccessUrl("/loginOk", true)	//로그인 성공시 이동할 페이지를 요청
					.failureHandler(new CustomAuthenticationFailureHandler())	//로그인  실패 핸들러
					.permitAll()	//로그인 페이지 접근 허용
					)
			.logout(logout -> logout
					.logoutUrl("/logout.do")	//로그아웃 URL 설정
					
					.logoutSuccessHandler((request, response, authentication)->{
						response.setStatus(HttpServletResponse.SC_OK);	//200상태 코드 포함
						response.setContentType("application/json");
						response.getWriter().write("{\"message\":\"Logout Success\"}");	//로그아웃 성공 메시지
						response.getWriter().flush();
					})
					
					
					.invalidateHttpSession(true) 	//HTTP 세션 무효화
					.deleteCookies("JSESSIONID", "remember-me-cookie") 	//쿠키 삭제, remember-me 쿠키 삭제
					)
		.rememberMe(rememberMe -> rememberMe
										.key("remember-me")	//화면에서 (LoginComponent.jsx)에서 체크박스의 name과 같이 formData이름과 같아야한다
										.tokenValiditySeconds(180) 	//3분, 하루 60*60*24
										.userDetailsService(userDetailService)
										.rememberMeParameter("remember-me") 	//화면의 remember-me checkbox의 이름
										.rememberMeCookieName("remember-me-cookie") 	//생성할 쿠키의 이름
										)
		.sessionManagement(sessionManagement -> sessionManagement
										.maximumSessions(1)	//최대 허용 세션 수
										.maxSessionsPreventsLogin(false)	//세션 초과시 기존 세션을 만료
										.expiredSessionStrategy(new CustomExpriedSessionStrategy())
										);
		return http.build();
	}
	
	
	//서로 다른 도메인(출처)간의 자원 공유를 허용하기 위해서 사용
	@Bean
	public CorsFilter corsFilter() {
		return new CorsFilter(configurationSource());
	}
	
	
	//
	@Bean
	public CorsConfigurationSource configurationSource() {
		CorsConfiguration config = new CorsConfiguration();
		//react의 withCredenials : true 설정으로 반드시 특정 도메인 입력
		config.setAllowedOrigins(Arrays.asList("http://localhost:5173"));
		config.setAllowedMethods(Arrays.asList("GET", "POST", "DELETE", "PUT","PATCH","OPTIONS"));
		config.setAllowedHeaders(Arrays.asList("Authorization", "Cache-Control","Content-Type"));
		//Access-Control-Allow-Credentials 설정
		config.setAllowCredentials(true);
		
		//CORS 매핑 설정
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", config);
		return source;
		
	}
	
	//security Password Encoding을 통한 로그인 처리
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}







