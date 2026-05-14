package com.min.edu.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecutiryConfig {
	


	private final JwtFilter jwtFilter;

	SecutiryConfig(JwtFilter jwtFilter) {
		this.jwtFilter = jwtFilter;
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
		
		return http.csrf(csrf -> csrf.disable())	//csrf 비활성화
				
							.authorizeHttpRequests(request -> 
								request
								//TODO 004 로그인 요청 인증 제외
									.requestMatchers("/register", "/login", "/api/login", "/loginPage").permitAll()	//requestMatchers의 요청 주소는 인증을 하지 않겠다
									.anyRequest().authenticated())
									
							
							//.httpBasic(Customizer.withDefaults())	//Http Basic인증 활성화
							.sessionManagement(session ->
									session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))	//세션 관리 설정
							.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
							.build();
							
	}
	
	//비밀번호를 암호화하고 판단할 때 사용하는  PasswordEncoder 오버라이드 해서 암호화 되어있지 않은 값을 사용할 수 있도록 했다
//	@Bean
//	public PasswordEncoder loginPassword() {
//		return new NoPasswordEncoder();
//	}
	
	//비밀번호 암호화를 통해서 로그인을 처리
	@Bean
	public PasswordEncoder loginPassword() {
		return new BCryptPasswordEncoder();
	}
	
	//TODO 002 UserDetailsService가 자동으로 AuthenticaionManager를 구성해 주지만 세부적인 인증을 위해서 생성함
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) {
		return configuration.getAuthenticationManager();
	}
}






















