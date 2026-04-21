package com.min.edu.ctrl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.min.edu.model.service.IJobsService;
import com.min.edu.vo.JobsVo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

//TODO 010 화면의 요청을 처리하는 Controller
@Controller
@Slf4j
@RequiredArgsConstructor
public class HomeController {
	
	private final IJobsService service;
	
	//TODO 011 root context 요청
	@GetMapping("/")
	public String welcome() {
		log.info("HomeController Welcome GET");
		return "intro";
	}
	
	//TODO 012 selectJobs.do 전체조회
	@GetMapping(value="/selectJobs.do")
	public String selectJobs(Model model) {
		log.info("HomeController selectJobs.do 전체조회 GET");
		List<JobsVo> lists = service.allSelectJob();
		model.addAttribute("lists", lists);
		return "result";
	}
	
	//TODO 013 insertJobs.do 입력
	/*
	 * @RequestParam Map<String, Object> map 는
	 * <form>의 submit으로 전송되는 요청 처리
	 *  <input type="[]" name="키" value="값"
	 */
	@PostMapping(value = "/insertJobs.do")
	public String insertJobs(Model model, @RequestParam Map<String, Object>map) {
		log.info("HomeController insertJobs.do 전체조회 POST");
		int n = service.insertJob(map);
		model.addAttribute("insertResult", n);
		return "result";
		
	}
	
	//TODO 014 updateJobs.do 수정 GET
	@GetMapping(value = "/updateJobs.do")
	public String updateJobs(Model model) {
		log.info("HomeController updateJobs.do 수정 GET");
		int n = service.updateJob();
		model.addAttribute("updateResult", n);
		return "result";
		
	}
	
	//TODO 023 transaction.do 트랜잭션 POST
	// 	 * 예외로만 처리되면 되기 때문에 service의 반환이 의미가 없다
	//		컨트롤러에서 처리 해서 catch문에서 error로 처리 되도록 한다(JSP, Thymeleaf)
	//		비동기식처리(AJAX, @RestController, @ResponseBody) => 값을 반환 throw로 처리
	@PostMapping(value = "/transaction.do")
	public String transaction(Model model, @RequestParam Map<String, Object> map) {
		
		try {
			service.transaction(map);
			model.addAttribute("transactionResult", "SUCCESS");
			return "result";
		} catch (Exception e) {
			model.addAttribute("transactionResult", "FAIL");
			model.addAttribute("message", e.getMessage());
			return "error/error";
		}
	}
	
	
	//TODO 027 예외가 발생하는 Controller 요청
	@GetMapping("/text-error")
	public String test() {
		
		int a = 10/0;	//강제 에러 발생
		
		return "intro";	//윗줄들의 연산이 오류가 없을 경우는 Resolver에 의해서 Thymeleaf 화면을 호출
	}
	 
	
	
}











