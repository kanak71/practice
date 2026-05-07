package com.example.demo;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

// 화면에서 요청 받은 클래스
@CrossOrigin(origins = "http://localhost:5173/")
@RestController
public class FormController {
	
	@PostMapping("/api/form")
	public ResponseEntity<FormResponse> receiveForm(@RequestBody FormRequest request){
		// 화면에서 전달 받은 값을 출력
		System.out.println("성 :" + request.getFirstName());
		System.out.println("이름 :"  + request.getLastName()) ;
		System.out.println("이메일 : " + request.getEmail());
		
		//응답생성
		FormResponse response = 
				new FormResponse(request.getFirstName(), request.getLastName(), request.getEmail());
		
		//ResponseEctity 객체는 성공코드(200), 반환결과(data)를 같이 보내준다
		return ResponseEntity.ok(response);// 
	}
	
	
	@Getter
	@Setter
	@ToString
	static class FormRequest{
		private String firstName;
		private String lastName;
		private String email;
	}
	
	@Getter
	static class FormResponse{
		private String firstName;
		private String lastName;
		private String email;
		
		public FormResponse(String firstName, String lastName, String email) {
			super();
			this.firstName = firstName;
			this.lastName = lastName;
			this.email = email;
		}
	}

}
