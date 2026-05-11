package com.min.edu.comfig;

import java.util.Arrays;

import org.springframework.web.filter.CorsFilter;
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
					.requestMatchers("/loginProcess").permitAll()	//React에서 요청하는 페이지를 인증없이 접근
//					.anyRequest().permitAll()	//나머지 모든 요청 모두 접근 가능
					.anyRequest().authenticated()	//나머지 모든 요청은 인증 후에만 접근 가능
					)
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
					.logoutSuccessUrl("/login") 	//로그아웃 성공시 리다이렉트 할 URL 지정
					.invalidateHttpSession(true) 	//HTTP 세션 무효화
					.deleteCookies("JSESSIONID") 	//쿠키 삭제
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







