package com.min.edu.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import lombok.RequiredArgsConstructor;


//TODO 082 WebMvcConfigurer 스프링 부트에서 스프링 MVC의 기본설정을 바꾸거나 추가할 때 사용
@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {
	
	//인터셉터 주입
	private final CacheControllerInterceptor cacheControllerInterceptor;
	private final LoginInterceptor loginInterceptor;

	@Override
		public void addInterceptors(InterceptorRegistry registry) {
		
		//1. 로그인 체크 인터셉터
		registry.addInterceptor(loginInterceptor)
				.addPathPatterns("/**")
				.excludePathPatterns("/","/login.do","signupForm.do","/css/**", "/js/**", "img/**");
		
		//2. 캐시 제어 인터셉터
		registry.addInterceptor(cacheControllerInterceptor)
				.addPathPatterns("/boardList.do","/logout.do","/userDetail.do")
				.excludePathPatterns("/css/**", "/js/**", "img/**");
			
		}
}
