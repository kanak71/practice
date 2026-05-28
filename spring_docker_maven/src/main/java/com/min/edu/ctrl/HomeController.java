package com.min.edu.ctrl;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
	
	@GetMapping("/")
	public String Home() {
		System.out.println("SpringBoot 처음 페이지 이동 index");
		return "index";
	}

}
