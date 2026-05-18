package com.min.edu.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import com.min.edu.vo.Car;
import com.min.edu.vo.Owner;

@Configuration
//RepositoryRestConfigurer의  configureRepositoryRestConfiguration을 오버라이드하여 ID를 추가 할 수 있다
public class RestConfig implements RepositoryRestConfigurer {

	@Override
	public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {
		RepositoryRestConfigurer.super.configureRepositoryRestConfiguration(config.exposeIdsFor(Car.class, Owner.class),cors);
	}
}
