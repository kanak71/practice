package com.min.edu.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

//TODO 005 Subject의 정보를 담는 DTO
@Getter
@Setter
@AllArgsConstructor
@ToString
public class SubjectDto {
	
	private Long id;
	private String title;

}
