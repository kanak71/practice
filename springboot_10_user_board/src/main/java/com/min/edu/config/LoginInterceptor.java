package com.min.edu.config;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

//TODO 081 로그인 체크 Interceptor
@Slf4j
@Component
public class LoginInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		//요청경로
		String contextPath = request.getContextPath();
		String uri = request.getRequestId();
		String path = uri.substring(contextPath.length());
		
		log.info("요청 경로 : {}", path);
		
		HttpSession session = request.getSession();
		//로그인 체크
		if(session == null || session.getAttribute("loginVo") == null) {
			log.warn("인증 실패 => 로그인 페이지 이동 : {}", path);
			
			response.sendRedirect(contextPath+"/");
			return false;
		}
		
		return true;
	}

}
