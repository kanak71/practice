package com.min.edu.filter;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Strings;
import org.springframework.stereotype.Component;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

//TODO 083 Spring Controller를 진입하기 전에 실행되는 Filter
//@WebFilter(urlPatterns = "/*", filterName = "AccessLogFilter")
@Component
@Slf4j
public class AccessLogFilter implements Filter {

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		// 요청 정보를 출력
				HttpServletRequest request = (HttpServletRequest)req;
				
				// request의 객체 정보 : 요청주소(remoteAddr), 요청param(QueryString)
				// request의 Header 정보 
				//	 1) User-Agent : 요청된 사용자의 애플리케이션 타입, 운영체제,  브라우저 정보 등 식별
				//   2) Referer : 이전 페이지의 주소 A->B A를 통해서 B진입할때 사이트의 주소 애널리틱스 분석 
				//   3) 이전 cache정보 삭제 : Expires, Cache_Control, Pragma 
				
				//0. URI / URL 요청 주소 
				String uri = request.getRequestURI();
				String url = request.getRequestURL().toString();
				log.info("요청 URI : {}", uri);
				log.info("요청 URL : {}", url);
				
				//1. 접근 시도하는 주소를 출력
				String remoteAddr = request.getRemoteAddr();
				log.info("RemoteAddr : {}", remoteAddr );
				
				//2. 쿼리 스트링 GET에서 주소를 통해서 전달되는 키=값
				String queryString = request.getQueryString();
				queryString = StringUtils.defaultIfEmpty(queryString, "");
				log.info("QueryString : {}", queryString);
				
				//3. 이전 페이지 
				String referer = request.getHeader("Referer");
				referer = StringUtils.defaultIfEmpty(referer, "-");
				
				log.info("referer : {}",  referer);
				
				//4. 요청된 Client의 정보 // Guava 처리
				/*
				 *** UserAgent 읽는 방법
				 *   Mozilla/5.0 (Windows NT 10.0; Win64; x64)  - 의미 없음
					 AppleWebKit/537.36 (KHTML, like Gecko) - 렌더링 엔진 정보
					 Chrome/147.0.0.0 ★★★ Chrome 브라우저
					 Safari/537.36 WebKit 기반이라 같이 붙음
				 */
				
				String userAgent = request.getHeader("User-Agent");
				userAgent = com.google.common.base.Strings.nullToEmpty(userAgent);
				log.info("userAgent : {}" ,userAgent);
				
				jakarta.servlet.http.HttpServletResponse response = (jakarta.servlet.http.HttpServletResponse) resp;
				if ("85.137.56.240".equals(remoteAddr)) {
					log.warn("🚫 차단됨 (IP) : {}", remoteAddr);
					response.sendError(403, "Forbidden");
					return;
				}
				
				
				StringBuffer sb = new StringBuffer();
				sb.append(remoteAddr).append(":")
									.append(url)
									.append("?")
									.append(queryString)
									.append(":")
									.append(referer)
									.append(":")
									.append(userAgent);
		
				
				
		log.info("~(=^‥^)ノ  요청된 Client의 정보 : {}",sb.toString());
		
		
		
		chain.doFilter(req, resp);
	}
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		log.info("←_←  filter 들어왔습니다");
	}
	
	@Override
	public void destroy() {
		log.info("→_→ filter 나갑니다");
	}

}
