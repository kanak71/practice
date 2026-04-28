package com.min.edu.ctrl;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class ChatController {

	//TODO 001 첫페이지 요청
	@GetMapping("/")
	public String initPage() {
		log.info("채팅 처음 페이지 이동");
		return "index";
	}
	
	
	//TODO 009 1:N 채팅 요청처리 화면 chatOneToMany.do
	@GetMapping(value = "/chatOneToMany.do")
	public String chatOneToMany() {
		log.info("일대다 채팅화면 이동");
		return "chatOneToMany";
	}
	
}
