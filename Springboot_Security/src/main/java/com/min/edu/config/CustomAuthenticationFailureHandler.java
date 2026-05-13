package com.min.edu.config;

import java.io.IOException;
import java.io.PrintWriter;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); 	//401 Status로 반환
		response.setContentType("application/json; charset=UTF-8");	//반환타입을 JSON 형태로 반환 정의
		PrintWriter out = response.getWriter();
		out.write("{\"error\":\"잘못된 username 혹은 password 입니다\"}");	//{"error":"잘못된 username 혹은 password 입니다"}
		out.flush();
	}

}
