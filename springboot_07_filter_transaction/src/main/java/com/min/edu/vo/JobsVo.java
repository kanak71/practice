package com.min.edu.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

//TODO 003 VO 객체 (JOBS) 테이블
@Getter
@Setter
@ToString
public class JobsVo {

	private String job_id, job_title;
	private int min_salary, max_salary;
}
