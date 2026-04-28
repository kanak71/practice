package com.min.edu.socket;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import lombok.extern.slf4j.Slf4j;

//TODO 003 1:N WebSocket 객체를 생성하는 Handler Bean
/*
 * TextWebSocketHandler는 Spring에서 Websocket 텍스트 메시지를 처리 할 때 사용하는 기본  핸들러
 * STOMP 사용하지 않는 순수 WebSocket
 * 다른종류 : BinaryWebSocketHandler - 텍스트 말고 바이너리 데이터 처리(파일전송, 이미지, 영상...)
 * 			AbstractWebSocketHandler - 텍스트 + 바이너리 둘 다 처리
 * 			WebSocketHandler - 인터페이스이며 직접 모두 구현할 때 사용
 * 
 * 실무 : STOMP
 * 대규모작업(kafka) : STOMP+Broker
 */
@Component(value = "wsChat.do")
@Slf4j
public class MySocketHandler_OneToMany extends TextWebSocketHandler {

	//웹소켓 전체 세션을 담는다(채팅의 대상을 담음)
	private ArrayList<WebSocketSession> list;
	//웹소캣 세션에 해당 정보(이름)
	private Map<WebSocketSession, String> map = new HashMap<WebSocketSession, String>();
	
	public MySocketHandler_OneToMany() {
		list = new ArrayList<WebSocketSession>();
	}
	
	//TODO 004 화면에서 웹소캣을 생성했을 경오 생성되는 WebSocket 객체가 처음 호출되는 메소드
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		log.info("웹소캣 Connection 객체 생성, afterConnectionEstablished & WebSocket Session 생성");
		log.info("방금 참여한 WebSocket Session Open : {}", session.getId());
		
		super.afterConnectionEstablished(session);
		
		//전체 메시지를 보낼 때 사용하는 WebSocket 참여 객체의 모음 리스트
		list.add(session);
		log.info("현재 참여하고 있는 객체의 수 : {}", list.size());
		
		//WebSocketSession의 정보 출력
		Map<String, Object> map = session.getAttributes();
		log.info("----------------------- session.getAttribute -------------------------{}", map);
		
		
	}
	
	//TODO 005 화면에서 onclose(1005)를 통해서 WebSocket을 닫아준다. List 목록에서 삭제
	//		닫힘(onclose 요청, 브라우저 닫기..)이 발생했을 때 호출되는 메소드
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		log.info("웹소캣 세션 객체 삭제 afterConnectionClosed");
		super.afterConnectionClosed(session, status);
		
		log.info("웹소캣 세션 삭제 대상 : {}", session);
		list.remove(session);
		
		log.info("삭제 하고 남은 객체의 수 : {}", list.size());
		
		//화면에 메시지 보내주기
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH24:mm:ss");
		String out = sdf.format(new Date());
		//채팅에 참여하는 모든 WebSocket Session에 
		//*sendMessage <- new TextMessage
		for (WebSocketSession webSocketSession : list) {
			webSocketSession.sendMessage(new TextMessage("<font style='color:tomato; font-size:9px;'>"+map.get(session)+"님이 방을 나갔습니다.("+out+")</font>"));
		}
		
		log.info("웹소캣 Session을 통한 해당 인원 삭제 : ()", map.get(session));
		
		map.remove(session);
	}
	
	//TODO 006 WebSocket에 참여하는 대상자에게 메시지를 처리해 줌
	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		log.info("웹소캣 전달 메시지 : handleTextMessage");
		
		//TextMessage 객체에서 string은 getPalyload()를 통해서 출력된다
		String msg = message.getPayload();
		String msgToString = message.toString();
		
		log.info("전달된 메시지의 getPayload 값 : {}", msg);
		log.info("전달된 메시지의 getPayload 값 : {}", msgToString);
		
		//화면에서 전달한 값을 판단해서 입장과 채팅 내용을 구분(포함되어 있는 text)해서 화면으로 전송
		if(msg != null && !msg.equals("")) {
			
			if(msg.indexOf("#$nick_") != -1) {	//별명(nick)을 send 받았을 때 : 입장 메시지 전달
				
				map.put(session, msg.replace("#$nick_", ""));
				
				for (WebSocketSession webSocketSession : list) {
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH24:mm:ss");
					String out = sdf.format(new Date());
					webSocketSession.sendMessage(new TextMessage("<font style='color:green; font-size:9px;'>"+map.get(session)+"님이 입장하셨습니다.("+out+")</font>"));
				}
			} else {	//일반 채팅 내용, 알림
				String returnMsg = msg;
				if(msg.contains("!ALERT")) {
					returnMsg = "[ALERT]" + msg.replace("!ALERT", "");
				} else {
					returnMsg = "<font>"+msg+"</font>";
				}
				for (WebSocketSession webSocketSession : list) {
					webSocketSession.sendMessage(new TextMessage(returnMsg));
				}
				
			}
		}
		
		log.info("채팅 참여자 : {}", map);
		
		super.handleTextMessage(session, message);
	}
	
	
}














