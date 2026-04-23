package com.min.edu.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class JobsVo {
	
	private String job_id, job_title;
	private int min_salary, max_salary;

}
