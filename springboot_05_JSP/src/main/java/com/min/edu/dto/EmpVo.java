package com.min.edu.dto;

import lombok.Data;

//TODO 004 myBatis에서 사용하는 DTO/VO
@Data
public class EmpVo {
	
	private String empno;
	private String ename;
	private String job;
	private String mgr;
	private String hiredate;
	private String sal;
	private String comm;
	private String deptno;

}
