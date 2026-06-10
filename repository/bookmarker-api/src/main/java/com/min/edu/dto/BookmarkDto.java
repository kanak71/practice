package com.min.edu.dto;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// Entity의 속성 정보를 가지고 있는 객체를 생성
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookmarkDto {

	private Long id;
	private String title;
	private String url;
	private Instant createdAt;
	
}
