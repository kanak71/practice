package com.min.edu.socket;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import jakarta.servlet.ServletContext;
import lombok.extern.slf4j.Slf4j;

//TODO 016 그룹 WebSocket의 객체를 생성하는 Handler Bean
@Component(value = "wsChatGr.do")
@Slf4j
public class MySocketHandler_Group extends TextWebSocketHandler {
	
	@Autowired
	private ServletContext servletContext;
	
	private ArrayList<WebSocketSession> list;	//websocket Session을 담는 객체
	
	public MySocketHandler_Group() {
		list = new ArrayList<WebSocketSession>();
	}
	
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		log.info("그룹채팅 afterConnectionEstablished 접속 Websocket Session정보 : {}", session);
		super.afterConnectionEstablished(session);
		
		//bean 설정인 HttpSessionHandshakeInterceptor에 의해서 HttpSession 값을 사용할 수 있다
		/*
		 * 시나리오
		 * 로그인 -> HttpSession 생성 -> 자동으로 Spring Intercepter가 로그인된 HttpSession의 정보를 webSocket Session에 담아준다
		 * -> WebSocket Session에서 로그인된 정보를 사용할  수 있다
		 */
		list.add(session);	//전체 접속자 리스트에 새로운 접속자를 추가
		
		Map<String, Object> sessionMap = session.getAttributes();
		
		String grSession = (String)sessionMap.get("gr_id");
		String memSession = (String)sessionMap.get("mem_id");
		
		log.info("Client WebSocket Session의 개수 : {}", list.size());
		log.info("현재 접속 Session의 아이디 : {}", memSession);
		log.info("현재 접속 Session의 그룹 : {}", grSession);
	}
	
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		log.info("그룹채팅 afterConnectionClosed WebSocket Session 삭제");
		super.afterConnectionClosed(session, status);
		
		//현재 사용자 삭제
		Map<String, Object> mySession = session.getAttributes();	//WebSocket Session의 정보 + HttpSession 정보
		String myGrSession = (String)mySession.get("gr_id");
		String myMemSession = (String)mySession.get("mem_id");
		
		//전체 웹소캣 사용 멤버필드의 list에 담겨 있다
		log.info("삭제 전 확인 : {} , {}", list.contains(session), list.size());
		list.remove(session);
		log.info("삭제 후 확인 : {}, {}", list.contains(session), list.size());
		
		SimpleDateFormat sdf = new SimpleDateFormat();
		String now = sdf.format(new Date());
		
		//[중요] 여기에서 ServletContext의 리스트를 지워준다
		Map<String, List<String>> chatList = (Map<String, List<String>>)servletContext.getAttribute("chatList");
		if(chatList != null && myGrSession != null && myMemSession != null) {
			List<String> groupMember = chatList.get(myGrSession);
			if(groupMember != null) {
				groupMember.remove(myMemSession);	//여기서 안전하게 삭제
				log.info("WebSocket 종료 : {} 그룹에서 {} 제거:", myGrSession, myMemSession);
			}
		}
		
		//같은 그룹에 사용자에게 메시지 전달
		for (WebSocketSession s : list) {
			Map<String, Object> sessionMap = s.getAttributes();
			String otherGrSession = (String)sessionMap.get("gr_id");
			if(myGrSession.equals(otherGrSession)) {
				s.sendMessage(new TextMessage("<font style='color:blue; font-size:9px;'>"+myMemSession+"님이 퇴실하셨습니다("+now+")</font>"));
			}
		}
	}
	
	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		log.info("메시지 전달 message : {}", message.getPayload());
		
		String msg = message.getPayload();
		String txt = "";
		
		//내 정보 조회
		Map<String, Object> mySession = session.getAttributes();
		String myGrSession = (String)mySession.get("gr_id");	
		String myMemSession = (String)mySession.get("mem_id");
		
		//message에 따라서 구분 : 별명 / 내용
		if(msg.indexOf("#$nick_") != -1) {
			//입장정보를 나와 같은 그룹(myGrSession)의 대상에게 보낸다
			for (WebSocketSession s : list) {
				Map<String, Object> sessionMap = s.getAttributes();
				String otherGrSession = (String)sessionMap.get("gr_id");
				//나와 같은 그룹 판단
				if(myGrSession.equals(otherGrSession)) {
					s.sendMessage(new TextMessage("<font style='color:tomato; font-size:9px;'>"+myMemSession+"님 입장하셨습니다</font>"));
				}
			}
		} else { //일반 채팅 내용 전달
			String msg2 = msg.substring(0, msg.indexOf(":")).trim();
			
			for(WebSocketSession s : list) {
				//전체 WebSocket 참여자들의 정보를 가져옴(gr_id, mem_id)
				Map<String, Object> sessionMap = s.getAttributes();
				String otherGrSession = (String)sessionMap.get("gr_id");
				String otherMemSession = (String)sessionMap.get("mem_id");
				
				if(myGrSession.equals(otherGrSession)) {
					if(msg2.equals(otherMemSession)) {
						String newMsg = "[나]" + msg.replace(msg.substring(0, msg.indexOf(":")),"");
						log.info("내가 쓴 글 전달 내용 : {} ", newMsg);
						txt = newMsg;
					}else {
						String part1 = msg.substring(0, msg.indexOf(":")).trim();
						String part2 = "["+part1+"]"+msg.replace(msg.substring(0, msg.indexOf(":")+1), "");
						txt = part2;
					}
				}
			}
			
		}
		
		super.handleTextMessage(session, message);
	}
	
	
}










