package com.min.edu.ctrl;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpSession;
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
	
	//TODO 013 Group 채팅 참여 인원을 위한 화면 이동 chatGroup.do
	@GetMapping(value = "/chatGroup.do")
	public String chatGroup() {
		log.info("그룹 채팅 참여 인원 화면 이동");
		return "chatGroup";
	}
	
	//TODO 017 Group 채팅 화면 이동 windows open socketOpen.do
	@Autowired
	private ServletContext servletContext;	//참여하는 모든 사람의 정보가 공유되어야 삭제/확인/갱신이 가능하기 때문에 Application Scope를 사용
	
	@GetMapping(value = "/socketOpen.do")
	public String socketOpen(String gr_id, String mem_id, HttpSession session) {
		
		//Parameter의 정보 HttpSession에 담는 작업을 통해서
		//자동으로 Bean의 HandShakeHandler에 의해서 WebSocket Session에 담긴다
		// => new HttpSessionHandShakeInterceptor() 설정
		
		session.setAttribute("gr_id", gr_id);
		session.setAttribute("mem_id", mem_id);
		
		//서버 전체에 계속해서 참여자의 정보를 담기 위해서 Application Scope를 사용한다
		//ServletContext를 사용한다
		Map<String, List<String>> chatList = (Map<String, List<String>>)servletContext.getAttribute("chatList");
		//채팅방이 한번이라도 생성되었다면 "chatList" 있으면 사용, 없으면 새로 생성
		if(chatList == null) {
			chatList = new ConcurrentHashMap<String, List<String>>();
			servletContext.setAttribute("chatList", chatList);
		}
		
		List<String> groupMembers = chatList.get(gr_id);
		
		//처음 채팅방 생성시
		if(groupMembers == null) {
			//멀티스레드 환경에서 안정하게 사용하는 List
			//읽기가 많고, 쓰기는 적은 환경에서 사용한다
			groupMembers = new CopyOnWriteArrayList<String>();
			chatList.put(gr_id, groupMembers);
		}
		
		//채팅방은 있는데 내가 없다면 내가 참여할 수 있도록 아이디를 넣어 준다
		if(!groupMembers.contains(mem_id)) {
			groupMembers.add(mem_id);
		}
		
		log.info("{} 그룹에 {} 가 추가됨", gr_id, mem_id);
		log.info("웹소캣 목록 {}", servletContext.getAttribute("chatList"));
		
		return "chatGroupView";
	}
	
}









