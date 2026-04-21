package com.min.edu.ctrl;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import jakarta.servlet.http.HttpServletRequest;

//TODO 028 모든 컨트롤러의 try~catch를 대신하는 전역 예외 처리
@ControllerAdvice
public class GlobalExceptionHandler {
	
	// *구체적인 예외를 순서대로 작성해야 한다
	@ExceptionHandler(Exception.class)
	public String handleException(Exception e, Model model, HttpServletRequest req) {
		
		model.addAttribute("status", 500);
		model.addAttribute("path", req.getRequestURI());
		model.addAttribute("error", e.toString());
		model.addAttribute("message", "Controller의 전역 처리는 @ControllerAdvice 한다");
		
		return "error/error";
	}
}
