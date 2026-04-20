package com.min.edu.ctrl;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.min.edu.dto.EmpVo;
import com.min.edu.model.IEmpDao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller //요청처리 클래스 spring webMVC
@Slf4j
@RequiredArgsConstructor //생성자 주입 annotation
public class HomeController {

	private final IEmpDao empDao;	//생성자 주입 final로 작성
	
	/*
	 * doGet, /는 root context
	 * Spring Boot 가 실행되면 처음 호출하는 GET 요청 매핑
	 */
	@GetMapping("/")
	public String home() {	//반환타입 String은 ViewResolver 처리 된다 => application.properties 설정에 의해서 => /WEB-INF/views/[].jsp
		log.info("HomeController GET 처음 호출 요청");
		return "redirect:/empAll.do";	//spring에서 redirect는 Spring Container의 Controller를 다시 요청
		
	}
	
	//HttpServletRequest, HttpServletResponse => getRequestDispatcher([URL]).forward(request, response)
	// 값을 전달해주는 객체 => ui.Model
	//					HttpServletRequest 하고 싶어요 => 네 사용하세요
	@GetMapping("/empAll.do")
	public String getEmpAll(Model model) {
		log.info("HomeController empAll.do 실행");
		
		//생성한 Bean을 DI 하여 Dao를 실행시켜줌
		List<EmpVo> lists = empDao.getAllEmp();
		
		//model 객체에 값을 담아서 view로 전달할때는 addAttribute("키",값)을 사용한다
		model.addAttribute("lists", lists);
		return "empList";
	}
	
}
