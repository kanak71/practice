package com.min.edu.config;

import java.io.IOException;

import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletResponse;

public class CustomExpriedSessionStrategy implements SessionInformationExpiredStrategy {

	//session을 확인하여 확인된 Session이 없는 경우 403을 정보를 반환
	@Override
	public void onExpiredSessionDetected(SessionInformationExpiredEvent event) throws IOException, ServletException {
		HttpServletResponse response = event.getResponse();
		response.setStatus(HttpServletResponse.SC_FORBIDDEN);
		response.setContentType("application/json");
		response.getWriter().write("{\"message\":\"Session Expired. 로그인을 다시 해주세요\"}");
	}

}
