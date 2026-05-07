package com.min.edu.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


//TODO 003 사용자 예외처리
@ResponseStatus(value = HttpStatus.NOT_FOUND)	//데이터가 없을 때 마다
public class ResourceNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -1790298190059279524L;
	
	public ResourceNotFoundException(String message) {
		super(message);
	}
	
}
