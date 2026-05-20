package com.min.edu.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.min.edu.service.JwtService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

//요청 필터당 한번씩 호출하여 요청시 필터를 실행시킨다.
@Component
public class AuthenticationFilter extends OncePerRequestFilter{
	
	@Autowired
	private JwtService jwtService;
	
	@Autowired
	private ApplicationContext applicationContext;
	/*
	 * Filter의 흐름
	 * 1. JWT 인증에 성공
	 * 	 - JWT이 유효하고, 사용자 정보 올바르며, 인증 객체 SecurityContextHolder에 설정된다
	 *	 - Spring Security는 인증된 사용자로 요청을 계속 처리
	 *	 - 필터 및 컨트롤러로 요청이 전달
	 *2. JWT 인증 실패
	 *	- JWT이 유효하기 않거나 사용자 정보가 잘못된 경우, 인증 객체는 설정되지 않는다
	 *	- 요청은 인증되지 않은 상태로 계속 진행 -> 401(권한부족) 혹은 403 응답으로 처리
	 * 
	 */
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, 
									HttpServletResponse response, 
									FilterChain filterChain)
			throws ServletException, IOException {
		
		//1) Client로 부터 요청된 JWT 받아 준다(Header 정보로 들어 온다. 즉, Body가 아니다)
		String authHeader = request.getHeader("Authorization");
		String token = null;
		String username = null;
		
		//2) JWT에서 token 값만을 추출
		//Bearer란(소지자/운반자)? Bearer뒤에 있는 문자열 Token 입니다라고 표사해주는 것
		if(authHeader != null && authHeader.startsWith("Bearer ")) {
			//Token 남기고 자른다
			token = authHeader.substring(7);
			
			//3)
			username = jwtService.extractUsername(token);
			
			if(username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
				UserDetails userDetails = applicationContext.getBean(UserDetailsService.class)
											.loadUserByUsername(username);
				
				if(jwtService.validateToken(token, userDetails)) {
					UsernamePasswordAuthenticationToken authToken = 
							new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
					
					//securityContext 만듦
					//현재 요청(reuqest)의 상세 정보까지 포함해서
					//Spring Security에 인증 상태를 저장
					
					authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					SecurityContextHolder.getContext().setAuthentication(authToken);
				}
				
			}
			
		}
		
		//토큰 확인 인증도 완료 그렇다면 너의 흐름대로 이동해서
		filterChain.doFilter(request, response);
		
		
	}	// doFilterInternal끝

}
