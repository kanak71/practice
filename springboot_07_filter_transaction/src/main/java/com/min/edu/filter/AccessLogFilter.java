package com.min.edu.filter;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import ch.qos.logback.core.joran.action.SiftAction;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

//TODO 026 이동 흐름을 제어하는 필터
/*
 * 1) 특정 ip(내부ip)를 제외하고 사이트를 이용하지 못하게 만들때
 * 2) 위험한 IP를 차단할 때
 * 3) PV : 사이트를 들어오기 전의 사이트(흐름을 통해서 내 사이트를 알게 되었는지) - 분석
 * 4) 브라우저 정보 : 모바일 / 데스크탑
 *  ==> Remote Address : 요청 주소를 분석해서 처리 할 수 있는 Filter를 만든다
 */
@WebFilter(urlPatterns="/*", filterName = "AccessLogFilter")
@Component
@Slf4j
public class AccessLogFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest)request;
		
		String remoteAddr = StringUtils.defaultIfEmpty(req.getRemoteAddr(), "-");
		String url = StringUtils.defaultIfEmpty(req.getRequestURL().toString(), "-");
		String queryString = StringUtils.defaultIfEmpty(req.getQueryString(), "");
		
		String fullUrl = url;
		fullUrl += StringUtils.isNotEmpty(queryString) ? "?"+queryString : queryString;
		
		StringBuffer sb = new StringBuffer();
		sb.append(remoteAddr).append(":").append(fullUrl);
		
		log.info("요청된 Client 정보 : {}", sb.toString());
		chain.doFilter(request, response);
	}

}
