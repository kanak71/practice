package com.min.edu.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.min.edu.service.JWTService;
import com.min.edu.service.MyUserDetailsService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

//TODO 009 요청필터당 한번씩 호출하여 요청시 필터를 활성화하기 위해서 OncePerRequestFilter
@Component
public class JwtFilter extends OncePerRequestFilter {
	
	@Autowired
	private JWTService jwtService;
	
	@Autowired
	private ApplicationContext applicationContext;
	

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		//화면 만들다가 추가
		String path = request.getServletPath();

		System.out.println("현재 요청 경로 : " + path);

		if(path.equals("/loginPage") || 
		   path.equals("/api/login") || 
		   path.equals("/success") || 
		   path.equals("/register")) {

		    filterChain.doFilter(request, response);
		    return;
		}
		
		
		//1) Client로 부터 토큰을 받는다
		// ex) bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6InN1cGVybWFuIiwiYWRtaW4iOnRydWUsImlhdCI6MTUxNjIzOTAyMiwiZXhwIjoxNTE2MjQ5MDIyfQ.e_LM-uBNJ0G_dN9q5QnEyToO4z2vg0D40PakftRXbNw
		
		String authHeader = request.getHeader("Authorization");
		String token = null;
		String username = null;
		
		//2) JWT 토큰을 클라이언트 요청의 헤더에서 추출
		if(authHeader != null && authHeader.startsWith("Bearer ")) {
			token = authHeader.substring(7);	//Bearer 제외하고 JWT 값을 가져온다
			//token으로 부터 사용자의 이름을 추출한다
			username = jwtService.extractUserName(token);	//Token -> Claims -> playload -> 함수(getSubject : String)
			//																				함수(getExpiration: Data) => T타입으로 작성 => 함수형 인터페이스
			
			//3) JWT에서 사용자 이름을 추출하고 && 인증정보를 DB에 확인
			if(username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
				// UserDetailService를 통해서 username을 통해서 DB에서 조회
				UserDetails userDetails = applicationContext.getBean(MyUserDetailsService.class).loadUserByUsername(username);
				
				//4) JWT가 유효한 경우에는 인증객체(usernamePa...)를 생성하고 Spring Security 컨텍스트에 저장
				//	토큰 검증(토큰 만료) 
				if(jwtService.validateToken(token, userDetails)) {
					//Spring Security에게 현재 사용자는 인증된 사용자입니다 라고 Authentication에 객체를 생성 => Stateless로 되어 있으니깐
					UsernamePasswordAuthenticationToken authToken =
							new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
					authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					SecurityContextHolder.getContext().setAuthentication(authToken);
				}
			}
			
		}
		
		
		filterChain.doFilter(request, response);
	}
	

}
