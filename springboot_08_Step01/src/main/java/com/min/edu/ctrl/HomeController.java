package com.min.edu.ctrl;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

//TODO 004 요청에 따른 요청처리 @RequestMapping 작성 및 메소드 작성

/*
 * 1) 요청받는 처리는 @Controller가 처리 해줌
 * 2) 주소에 맞는 요청 @RequestMapping이 처리해준다
 * 3) 처리되는 프로토콜 요청 method=RequestMethod.GET 해당 RequestMapping이 GET 요청을 처리함을 선언한다
 *     ㄴ @GetMapping, @PostMapping, @PutMapping과 같은 방식으로 사용할 수 있다
 * 4) Spring에서 parameter의 처리는 메소드의 (parameter)로 처리 된다
 *    ㄴ Query String의 key 값과 같은 "parameter 변수명"을 사용하면 자동으로 값이 담기게 된다
 *    														자동으로 해당 타입으로 캐스팅 된다 => Auto Binding
 *    
 */

@Controller
@Slf4j
public class HomeController {
	
	//parameter는 자동으로 변수명과 타입(casting) 처리 된다
	@GetMapping("/")
	public String getMethodName(HttpServletRequest req, String param, int month, Model model) {
		log.info("파라미터 처리 방법");
		//모든 HTTP(Hyper Text Transfer Protocol)는 문자열로만 처리가 가능하다
		log.info("request 처리 방식 : {}, {} ", req.getParameter("param"), req.getParameter("month"));
		log.info("spring에서는 자동으로 Binding을 지원한다(타입 자동 형변환) : {}, {}", param, month);
		
		//값 전달 : page, request, session, application
		// ui.Model 객체는 HttpServletRequest를 기반으로 한다
		
		model.addAttribute("req", "ui,Model 객체는 Request Scope 이다");
		req.setAttribute("req2", "HttpServletRequest Scope");
		//이동흐롬 : requestDispatcher().forward(request, response); => 객체전달 JSP
		//			response.sendRedirect(); => 값전달 AJAX
		
		return "param";	//=> ViewResolver 를 통해서 자동으로 화면으로 요청된다
	}
}












