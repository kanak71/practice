package com.min.edu.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateBookmarkRequest {
	
	
	@NotEmpty(message = "제목은 필수 입력값 입니다")
	private String title;
	@NotEmpty(message = "URL은 필수 입력값 입니다")
	private String url;

}
