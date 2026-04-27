package com.min.edu.session.ctrl;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

//TODO 003 sessionInit.do
@Controller
@Slf4j
@SessionAttributes("sessionTest")	//TODO 004 @SessionAttributes를 통해서 Spring이 관리하는 Session을 생성
public class Session_01_Controller {
	
	//TODO 005 Spring Session과 HttpSession을 생성하는 곳
	@GetMapping(value = "/sessionInit.do")
	public String sessionPage(Model model, HttpSession session) {
		log.info("Session_01_Controller sessionInit.do GET : HttpSession, @SessionAttributes 값 생성");
		
		session.setAttribute("httpSessionText", "^^* sessionInit.do 에서 입력된 HttpSession");
		model.addAttribute("sessionTest", "sessionInit.do에서 @SessionAttributes");
		
		return "sessionCheck";
	}
	
	//TODO 008 sessionStatus.setComplete() 삭제 요청
	@GetMapping("/test01.do")
	public String result01(SessionStatus sessionStatus) {
		log.info("Session_01_Controller test01.do : Spring Session 삭제");
		sessionStatus.setComplete();
		return "sessionCheck";
	}
	
	//TODO 010 삭제 결과 확인
	@GetMapping("/result01.do")
	public String result01(HttpSession session,
						@SessionAttribute(value="sessionTest", required = false) String sessionTest) {
		log.info("각 Session Session_01_Controller에서 출력");
		System.out.println("\t" + session.getAttribute("httpSessionTest"));
		System.out.println("\t" + sessionTest);
		
		return "sessionCheck";
		
	}
	
	//TODO 012 HttpSession을 invalidate() 삭제
	@GetMapping("/test02.do")
	public String test02(HttpSession session,
			@SessionAttribute(value="sessionTest", required = false) String sessionTest) {
		log.info("HttpSession을 삭제하는 invalidate를 통해서 삭제하면 Spring Session도 영향을 받을까?");
		
		session.invalidate();
		return "sessionCheck";
		
	}
	

	
}











