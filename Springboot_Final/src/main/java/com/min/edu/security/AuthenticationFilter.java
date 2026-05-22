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

// 요청 필터당 한번씩 호출하여 요청시 필터를 실행시킨다. => OncePerRequestFilter
@Component
public class AuthenticationFilter extends OncePerRequestFilter{

	@Autowired
	private JwtService jwtService;
	
	@Autowired
	private ApplicationContext applicationContext;
	
	/*
	 * Filter의 흐름 
	 * 1. JWT 인증에 성공 
	 *   - JWT 이 유효하고, 사용자 정보 올바르며, 인증 객체 SecurityContextHolder에 설정된다
	 *   - Spring Security는 인증된 사용자로 요청을 계속 처리
	 *   - 필터 및 컨트롤러로 요청이 전달
	 * 2. JWT 인증 실패
	 *   - JWT 이 유효하지 않거나. 사용자 정보가 잘못된경우, 인증 객체는 설정되지 않는다
	 *   - 요청은 인증되지 않은 상태로 계속 진행 -> 401(권한부족) 혹은 403 응답으로 처리
	 */
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, 
									HttpServletResponse response, 
									FilterChain filterChain)
			throws ServletException, IOException {
		
		//1) Client로 부터 요청 된 JWT 받아 준다(Header 정보로 들어 온다. 즉, Body가 아니다)
		String authHeader = request.getHeader("Authorization"); //Bearer eyJhbGciOiJIUz....
		String token = null;
		String username = null;
		
		//2) JWT에서 token값만을 추출
		// Bearer(소지자/운반자) 란? Bearer뒤에 있는 문자열 Token입니다라고 표시해주는 것
		if(authHeader != null && authHeader.startsWith("Bearer ")) {
			// Token 남기고 자른다
			token = authHeader.substring(7); // substring은 start index를 값을 포함, end inexe는 -1
			
			//3)token으로 부터 이름을 추출 : JwtService > 68줄
			username = jwtService.extractUsername(token);
		}
		
		//4) JWT 추출된 이름으로 인증정보를 판단해 준다
		//   username으로 인증 UserDetailsService 를 통해서 UserDetails 를 가져온다
		
		if(username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			// 필터 이기 때문에 전체의 bean을 관리하는(@SpringBootApplication) 
			//                              => ApplicationContext 모든 bean들이 들어가 있다
			//                                 getBean([타입을 작성한다])
			UserDetails userDetails = applicationContext.getBean(UserDetailsService.class)
										.loadUserByUsername(username);
			
			//5) JWT가 유효한지를 판단
			// 토큰검증(토큰 만료, 토큰가진 정보가 사용자의 것인지 확인)
			if(jwtService.validateToken(token, userDetails)) {
				// (principal 객체, 비밀번호null, 궈한) 
				UsernamePasswordAuthenticationToken authToken =
						new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
				
				
				// SecurityContext 만듬
				// 현재 요청(request)의 상세 정보까지 포함해서
				// Spring Security에 인증 완료 상태를 저장
				authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authToken);
				
			}
		}
		// 토큰 확인 인증도 완료 그렇다면 너의 흐름으로 대로 이동해서
		filterChain.doFilter(request, response);
		
	}// doFilterInternal 끝
}









