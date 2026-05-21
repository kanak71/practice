package com.min.edu.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import com.min.edu.vo.Car;
import com.min.edu.vo.Owner;

// RepositoryRestConfigurer의 정의된 configureRepositoryRestConfiguration를 오버라이드 ID를 추가 할 수 있다
@Configuration
public class RestConfig implements RepositoryRestConfigurer {

	
	@Override
	public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {
		RepositoryRestConfigurer.super
									.configureRepositoryRestConfiguration(config.exposeIdsFor(Car.class, Owner.class)
																		, cors);
		
		cors.addMapping("**") 	//Spring REST API의 기본 주소는 applicaiton.properties에서 정의, 요청이름 허용
				.allowedOrigins("http://localhost:5173/")	//허용될 출처를 지정
				.allowedMethods("GET","POST","PUT","DELETE","PATCH","OPTIONS") //허용할 HTTP 메소드 요청 설정
				.allowCredentials(true)	//자격인증 증명(쿠키, 인증정보 등) 허용 설정
				.allowedHeaders("*") 	//허용할 헤더 설정
				//JWT 헤더를 프론트에서 읽기 위해서 설정
				.exposedHeaders("Authorization")
				.maxAge(3600); 	//미리 요청을 캐쉬 3600초(1시간)
		
		/*
		 * 미리 요청을 캐쉬
		 * 브라우저가 CORS 요청 결과를 캐시하는 시간
		 * 응답을 몇초동안 재사용할지
		 */
		
	}
}
