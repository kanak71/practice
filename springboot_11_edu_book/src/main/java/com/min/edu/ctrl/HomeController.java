package com.min.edu.ctrl;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
	
	//TODO 001 HomeController 첫화면 index.html 이동
	@GetMapping("/")
	public String home() {
		System.out.println("첫페이지 이동");
		return "index";
	}

}
