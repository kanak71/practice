package com.min.edu.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import jakarta.servlet.DispatcherType;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

	private final CustomOAuth2UserService customOAuth2UserService;
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
			.csrf(csrf -> csrf.disable())
			.authorizeHttpRequests(request-> request
										.dispatcherTypeMatchers(DispatcherType.FORWARD).permitAll()	
										//FORWARD 디스패쳐에 대해서 허용, Controller -> View(JSP Thymeleaf)로 이동하는 내부 요청
										.requestMatchers("/").permitAll()	//추가적인 요청을 인증을 제외
										.anyRequest().authenticated()	//모든 요청에 대해서 인증 필요
								)
			
			
			//일반로그인 처리
			.formLogin(form -> form
								.loginPage("/login")
								.loginProcessingUrl("/login")
								.defaultSuccessUrl("/")
								.permitAll()
					)
							
			//OAuth2 로그인 처리
			.oauth2Login(oauth2 -> oauth2
									.defaultSuccessUrl("/infoUser", true)	//로그인 후 /infoUser로 리다이렉션
									
									//사용자 정보를 처리할 커스텀 서비스를 지정(DB확인 update 혹은 save) 
									.userInfoEndpoint(userInfo -> userInfo
																		.userService(customOAuth2UserService)
											)
								)
			.logout(logout -> logout
								.logoutSuccessUrl("/")	//로그아웃 후 홈으로 이동
								.invalidateHttpSession(true)
								.deleteCookies("JSESSIONID")
						);
					
				return http.build();
	}
}
