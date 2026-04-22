package com.min.edu.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

//TODO 004 BoardVo
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoardVo {
	
	private int seq;
	private String id;
	private String title;
	private String content;
	private int ref;
	private int step;
	private int depth;
	private String regdate;
	private String delflag;
}
