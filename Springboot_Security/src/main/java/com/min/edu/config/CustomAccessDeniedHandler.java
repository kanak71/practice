package com.min.edu.config;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tools.jackson.databind.ObjectMapper;

public class CustomAccessDeniedHandler implements AccessDeniedHandler {

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException, ServletException {
		//응답 상태 코드를 403으로 설정
		response.setStatus(HttpServletResponse.SC_FORBIDDEN);
		//응답의 Content-Type을 JSON 변경
		response.setContentType("application/json; charset=UTF-8;");
		//클라이언트에 반환할 JSON 응답 객체 생성
		PrintWriter out = response.getWriter();
		
		Map<String, Object> errorResponse = new HashMap<String, Object>();
		
		errorResponse.put("error", "Access Denied");
		errorResponse.put("message", "접근 권한이 없는 리소스에 접근했습니다");
		errorResponse.put("timestamp", System.currentTimeMillis());
		errorResponse.put("path", request.getRequestURI());
		
		//springboot의 Spring mvc에 자동으로 객체를 JSON을 변경하는 jackson-bind를 사용해서 변경
		ObjectMapper objectMapper = new ObjectMapper();
		
		out.write(objectMapper.writeValueAsString(errorResponse));
		//결론 : escape 문자열 방식 혹은 Map/objectMapper 방식 둘 다 중요한건 JSON 모양의 문자열로 만들어야 한다
		//실수 : DTO를 만들어서 다른 Handler들도 같은 모양의 값을 입력받을 수 있도록 한다 + Builder Pattern
	}

}
