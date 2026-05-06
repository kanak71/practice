package com.min.edu.entity;


import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Student {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String name;
	
	//TODO 004 과목과 다대다관계를 설정
	@JsonIgnore	//재귀연결을 제거하기 위해서 사용
	@ManyToMany(mappedBy = "enrolledStudent")
	
	//TODO 002 학생 조회시 과목의 정보를 조회
	private Set<Subject> subjects = new HashSet<Subject>();
	
	public Set<Subject> getSubjects(){
		return subjects;
	}
	
	

}
