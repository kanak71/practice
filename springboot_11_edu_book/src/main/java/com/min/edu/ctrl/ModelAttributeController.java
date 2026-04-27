package com.min.edu.ctrl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import lombok.extern.slf4j.Slf4j;

//TODO 102 ModelAttribute Controller
@Controller
@Slf4j
public class ModelAttributeController {

	//TODO 103 현재 컨트롤러의 모든 곳에서 "userType"이라는 이름으로 리스트를 사용할 수 있도록 한다
	//			**해당 컨트롤러가 실행 될 때 가장 먼저 실행된다
	//			사용예시 : 로그인된 정보를 Session -> Object -> DTO -> getId() 를 미리 작성해서 사용할 수 있다
	
	@ModelAttribute("userType")
	public List<String> referenceData(){
		List<String> types = new ArrayList<String>();
		types.add("관리자");
		types.add("일반유저");
		types.add("게스트");
		return types;
	}
	
	//TODO 104 요청된 ModelAttribute.do 에서 @ModelAttribute를 자동으로 가져간다
	// "@ModelAttribute는 컨트롤러가 실행 전에 호출되어 Model에 값을 담고 있다
	@GetMapping("/modelAttribute.do")
	public String modelAttributeUse(Model model) {
		log.info("@ModelAttribute는 컨트롤러가 실행 전에 호출되어 Model에 값을 담고 있다");
		
		List<String> lists = (List<String>)model.getAttribute("userType");
		System.out.println("\n");
		System.out.println(lists);
		
		return "modelAttributeUse";
	}
}








