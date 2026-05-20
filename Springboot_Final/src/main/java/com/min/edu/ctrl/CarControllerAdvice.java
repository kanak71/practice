package com.min.edu.ctrl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import lombok.extern.slf4j.Slf4j;

// REST Controller 오류 발생시 JSON으로 응답
@ControllerAdvice
@Slf4j
public class CarControllerAdvice {
	
	@ExceptionHandler()
	public ResponseEntity<Map<String, Object>> handleAuthenticastionException(AuthenticationException ex){
		log.error("인증 실패: 사용자 이름, 비밀번호가 올바르지 않습니다");
		
		Map<String, Object> response = new HashMap<String, Object>();
		response.put("status", HttpStatus.UNAUTHORIZED.value());
		response.put("error", "유효하지 않은 인증");
		response.put("msg", "사용자 이름, 비밀번호가 올바르지 않습니다");
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
	}
	
	
	
	// NullPointExption 처리(차동차 정보가 없을 경우)
	@ExceptionHandler(NullPointerException.class)
	public ResponseEntity<Map<String, Object>> hadleNullPointException(NullPointerException ex){
		log.error("자동차 정보가 없습니다. (NullPointerException 발생) : {}", ex);
		
		//응답 객체를 생성하여 JSON 형태로 반환
		Map<String, Object> response = new HashMap<String, Object>();
		response.put("status", HttpStatus.NOT_FOUND.value());
		response.put("msg", "자동차 정보가 없습니다");
		//상태(Status) + 메시지 값(Body)를 객체 => jackson-bind 라이브러리 + spring web => JSON 같이 보내준다
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
	}
	
	@ExceptionHandler(NoResourceFoundException.class)
	public ResponseEntity<Map<String, Object>> handleNoResourceFoundException(NoResourceFoundException ex){
		log.info("정적 리소스를 찾을 수 없습니다. : {}" , ex);
		
		Map<String, Object> response = new HashMap<String, Object>();
		response.put("status", HttpStatus.NOT_FOUND.value());
		response.put("msg", "요청한 리소스를 찾을 수 없습니다. 요청한 경로를 확인해 주세요");
		//상태(Status) + 메시지 값(Body)를 객체 => jackson-bind 라이브러리 + spring web => JSON 같이 보내준다
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
	}
	
	
	// 모든 예외를 처리 하는 Exception
	@ExceptionHandler(Exception.class)
	public ResponseEntity<Map<String, Object>> handleException(Exception ex){
		log.error("자동차 조회 중 오류 발생");
		
		Map<String, Object> response = new HashMap<String, Object>();
		response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value()); // Status Code : 500 서버 상태 오류
		response.put("msg", "자동차 정보를 조회하는 도중에 서버 오류가 발생 했습니다. 다시 시도해 주세요");
		//상태(Status) + 메시지 값(Body)를 객체 => jackson-bind 라이브러리 + spring web => JSON 같이 보내준다
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
	}
	
	
}








