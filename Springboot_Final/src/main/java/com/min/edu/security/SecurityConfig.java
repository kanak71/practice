package com.min.edu.security;

import org.springframework.security.config.Customizer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password4j.BcryptPassword4jPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	/*
	 * Bean을 만들어서 SpringSecurityFilter를 작성해서 환경설정, 인증/분기/로그인/로그아웃/리멤버미/세션...
	 * 선언하지 않으면 default로 동작이 된다
	 * http
	 * 	.authorizeHttpRequest(auth -> auth.anyRequest().authentcated()	//모든 요청은 인증이 있어야 한다
	 * .formLogin(withDefaults())
	 * .httpBasic(withDefaults)
	 */
	
	private final AuthEntryPoint authEntryPoint;
	private final AuthenticationFilter authenticationFilter;

	SecurityConfig(AuthenticationFilter authenticationFilter, AuthEntryPoint authEntryPoint) {
		this.authenticationFilter = authenticationFilter;
		this.authEntryPoint = authEntryPoint;
	}


	/*
	 * SecurityFilterChain을 통해서 POST 요청의 /login은 인증이 없어도 요청이 처리되어야 한다(permitAll)\
	 * Security가 세션을 생성하지 않도록 정의하여 csrf를 비활성화
	 */
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
//		return http
//				.csrf(csrf->csrf.disable())
//				.authorizeHttpRequests(request ->
//										request
//										 	.requestMatchers("/login").permitAll()	//Controller의 /login은 인증이 없어도 허용
//										 	.anyRequest().authenticated()	//그 외의 요청(Spring REST API - /api등) 인증 필요
//										 	
//										 	)
//				.httpBasic(httpBasic -> httpBasic.authenticationEntryPoint(authEntryPoint))	//기존의 인증방식은 username/password를 base64로 인코딩하여 서버로 전달하는 방법
//				.sessionManagement(session -> 
//										session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//				//Filter를 통한 JWT인증전에 실행(전처리 필터 선언 - JWT 인증 처리)
//				.addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class)
//				.build();
		
		//프론트 작업 테스트용
		return http
				.csrf(csrf -> csrf.disable())
				.authorizeHttpRequests(request-> request.anyRequest().permitAll())
				.build();
	}
	

	// AuthentcationManager : JWT를 사용하기 위해서 선언
	/*
	 * 로그인 시도시 UsernamePasswordAuthenticationToken을 통해서 Security에 전달하고 AuthenticationManager가 정보를 검증한다
	 * 인증이 성공하면 사용자 객체를 반환(UserDetails>User), 실패하면 예외 던져진다
	 * AuthenticationManager는 인증을 수행하기 위해서 UserDetailsService를 사용하여 사용자 정보를 조회, 비밀번호를 확인
	 */
	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception{
		return authenticationConfiguration.getAuthenticationManager();
	}
	
	// PasswordEncoder 
	/*
	 * setDetailsService가 반환한 UserDetails 객체에서 저장된 암호화된 비밀번호와 전달받은 사용자 입력한 원본의 비밀번호를 PasswordEncoder를 통해서 비교
	 * 
	 */
	@Bean
	public PasswordEncoder passwordEncoder() {
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder;
	}
	
}
