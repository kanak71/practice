package com.min.edu.session.ctrl;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.support.SessionStatus;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class Session_02_Controller {

	//TODO 014 @SessionAttributes를 생성하지 않은 Controller에서 setComplete를 실행하여 삭제
	@GetMapping("/test03.do")
	public String test03(SessionStatus sessionStatus,
					@SessionAttribute(value="sessionTest", required = false) String sessionTest) {
		log.info("다른 컨트롤러에서 @SessionAttributes 사용할 수 있을까? {}" , sessionTest);
		log.info("다른 컨트롤러에서 SessionStatus를 통해서 @SessionAttribute를 삭제한다");
		sessionStatus.setComplete();
		return "sessioncheck";
	}
	
	//TODO 017 다른 컨트롤러에서 HttpSession을 삭제
	@GetMapping("/test04.do")
	public String test04(HttpSession session) {
		log.info("다른 컨트롤러에서 HttpSession을 삭제한다");
		
//		session.removeAttribute("sessionTest");
//		session.removeAttribute("httpSessionTest");
		
		session.invalidate();
		
		
		
		return "sessioncheck";
	}
}
