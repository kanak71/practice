package com.min.edu.dto;


import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

//TODO 302 특정 교수의 담당 과목 조회를 위한 프로덕션 DTO
@Getter
@Setter
@ToString
public class TeacherDto {
	
	private Long teacher_id;
	private String teacherName;
	private List<SubjectDto> subjects;
	
	public TeacherDto(Long teacher_id, String teacherName, List<SubjectDto> subjects) {
		super();
		this.teacher_id = teacher_id;
		this.teacherName = teacherName;
		this.subjects = subjects;
	}
	
	
	
	
	

}
