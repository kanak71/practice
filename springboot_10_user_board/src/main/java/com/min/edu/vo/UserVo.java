package com.min.edu.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

//TODO 004 UserVo
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserVo {
	private String id;
	private String name;
	private String password;
	private String email;
	private String auth;
	private String enable;
	private String joindate;
}
