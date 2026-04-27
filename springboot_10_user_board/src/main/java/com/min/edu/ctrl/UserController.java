package com.min.edu.ctrl;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.min.edu.model.service.IUserService;
import com.min.edu.vo.UserVo;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

//TODO 011 회원관리 Controller

@Controller
@Slf4j
@RequiredArgsConstructor
public class UserController {

	private final IUserService service;
	
	//TODO 014 로그인 폼 화면 이동
	//	context root의 요청을 화면으로 이동시켜
	
	@GetMapping("/")
	public String loginForm() {
		log.info("UserController 로그인 화면 이동");
		return "loginForm";
	}
	
	//TODO 017 로그인 정보 처리 및 로그인 후 화면 이동
	/*
	 * HttpServletResponse 설정
	 * 인코딩처리 위해서 application.properties에 Encoding 설정을 했지만, jakarta.servlet.*. 사용하기 때문에
	 * 결론 : SpringBoot 인코딩과는 전혀 관련이 없다
	 * 따라서 기본인코딩(ISO-8859-1) 처리 된다
	 * 해결 방법 HpptServletResponse를 인코딩 처리해 주면 된다
	 * 권장되는 방법은 아니다 param을 통해서 전달해서 화면에서 script를 통해서 실행해야 한다
	 */
	@PostMapping(value="/login.do")
	public String login(@RequestParam Map<String, Object> map,
							HttpSession session, 
							HttpServletResponse response) throws IOException {
		log.info("UserController login.do 로그인 {}", map);
		
		response.setContentType("text/html; charset=UTF-8;");
		
		UserVo loginVo = service.getLogin(map);
		
		if(loginVo != null) {
			session.setAttribute("loginVo", loginVo);
			session.setMaxInactiveInterval(60*10*5);
			log.info("{}님 로그인 되었습니다", loginVo.getName());
			response.getWriter().print("<script>alert('"+loginVo.getName()+"님 반갑습니다'); location.href='./boardList.do';</script>");
		} else {
			response.getWriter().print("<script>alert('로그인 정보가 없습니다'); location.href='./';</script>");
		}
		
		return null;
	}
	
	//TODO 022 로그아웃 요청 logout.do
	@GetMapping(value="/logout.do")
	public String logout(HttpSession session, HttpServletResponse response) {
		log.info("UserController logout.do GET 로그아웃");
		
		response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
		response.setHeader("Pragma", "no-cache");
		response.setDateHeader("Expires", 0);
		session.invalidate();	//세션을 무효화
		
		return "redirect:/";
	}
	
	//TODO 024 회원가입 화면 이동 signupForm.do
	@GetMapping(value="/signupForm.do")
	public String signupForm() {
		log.info("UserController signupForm.do GET 회원가입 화면");
		return "signupForm";
	}
	
	//TODO 026 회원가입 ID중복검사 화면 window open
	@GetMapping(value="/duplication.do")
	public String duplication() {
		log.info("UserController duplication.do GET 아이디 중복검사 회면");
		return "duplication";
	}
	
	//TODO 034 회원가입 정보 입력 signUp.do
	@PostMapping(value = "/signUp.do")
	public String signUp(UserVo vo, HttpServletResponse response) throws IOException {
		log.info("UserController singUp.do POST 회원가입 입력 : {}", vo);
		int cnt = service.signupMember(vo);
			
		if(cnt == 1) {
			log.info("회원가입 여부 : {}", (cnt>0)? "가입성공":"가입실패");
			return "redirect:/";
		} else {
			response.setContentType("text/html; charset=UTF-8;");	//servlet의 인코딩
			response.getWriter().print("<script>alert('로그인 정보가 없습니다'); location.href='./';</script>");	//@ResponseBody
			return null;
		}
	}


	//TODO 038 아이디 찾기 findidWindow.do
	@GetMapping(value = "/findIdWindow.do")
	public String findIdWindow() {
		log.info("UserController findIdWindow.do GET 아이디 조회");
		return "findId";
	}
	
	//TODO 068 회원 전체 조회 userSelectAll.do
	@GetMapping(value="/userSelectAll.do")
	public String userSelectAll(Model model) {
		log.info("UserController userSelectAll.do [관리자] 회원전체조회 화면 이동");
		
		List<UserVo> userList = service.userSelectAll();
		model.addAttribute("userList", userList);
		
		return "userSelectAll";
	}
	
	//TODO 075 회원 관리 조회 managementUser.do
	@GetMapping(value = "/managementUser.do")
	public String managementUser(Model model) {
		log.info("UserController managementUser.do GET 회원관리 이동");
		List<UserVo> userList = service.getAllUser();
		model.addAttribute("userList", userList);
		
		return "managementUser";
	}



}














