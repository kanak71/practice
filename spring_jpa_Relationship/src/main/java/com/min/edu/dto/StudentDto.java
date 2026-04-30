package com.min.edu.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

//TODO 006 Student 정보와 Subject 정보를 List로 담고 있는 DTO
@Getter
@Setter
@AllArgsConstructor
@ToString
public class StudentDto {
	
	private Long id;
	private String name;
	
	private List<SubjectDto> subject;
}
