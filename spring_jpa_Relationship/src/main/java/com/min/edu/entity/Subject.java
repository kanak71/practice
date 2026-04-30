package com.min.edu.entity;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Subject {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String title;
	
	
	//TODO 003 과목은 여러명의 학생과 관계가 되어 있으므로 "다대다"로 선언하고, 
	//				joinTable을 통해서 subject와 student의 id를 통해 구성
	
	@ManyToMany
	@JoinTable(
			name = "student_enrolled",
			joinColumns = @JoinColumn(name="subject_id"),
			inverseJoinColumns = @JoinColumn(name="studend_id")
			)
	
	
	//TODO 001 학생정보를 담을 수 있는 컬럼 생성
	private Set<Student> enrolledStudent = new HashSet<Student>();
	
	public Set<Student> getEnrolledStudent() {
		return enrolledStudent;
	}
	
	
	
}
