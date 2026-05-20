package com.min.edu.vo;

import lombok.Getter;
import lombok.Setter;

//인증을 위한 자격증명(username, password)를 포함하는 DTO
//로그인 요청(JSON) 객체로 받아서 인증 처리에 사용할 DTO 클래스

@Getter
@Setter
public class AccountCredentials {
	
	private String username;
	private String password;
	

}
