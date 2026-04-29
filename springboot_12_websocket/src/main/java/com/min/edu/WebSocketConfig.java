package com.min.edu;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import com.min.edu.socket.MySocketHandler_Group;
import com.min.edu.socket.MySocketHandler_OneToMany;

import lombok.RequiredArgsConstructor;

//TODO 007 @Configuration을 통해서 WebSocket Handler Bean을 등록한다
@Configuration
@EnableWebSocket	//웹소캣 활성화
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketConfigurer {

	private final MySocketHandler_OneToMany oneToMany;
	private final MySocketHandler_Group group;
	
	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		//TODO 008 1:N의 동작을 위한 Bean을 환경설정에 등록
		registry.addHandler(oneToMany, "/wsChat.do")
			.setAllowedOrigins("*");	//CORS 설정(필요에 따라 조정)
		
		
		//TODO 017 Group 핸들러 등록
		registry.addHandler(group, "/wsChatGr.do")
			.addInterceptors(new HttpSessionHandshakeInterceptor())	//HttpSession이 자동으로 WebSocket Session에 등록되도록 만듦
			.setAllowedOrigins("*");
	}

}
