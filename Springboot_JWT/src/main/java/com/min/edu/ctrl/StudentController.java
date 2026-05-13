package com.min.edu.ctrl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.min.edu.model.Student;

import jakarta.servlet.http.HttpServletRequest;

@RestController
public class StudentController {
	
	private List<Student> students = new ArrayList<Student>(
			List.of(
					new Student(1, "Navi", 60),
					new Student(2, "Kiran", 70)
					)
			);
	
	//전체조회
	@GetMapping("/students")
	public List<Student> getStudents(){
		return students;
	}
	
	//입력
	@PostMapping("/students")
	public Student addStudent(@RequestBody Student student) {
		students.add(student);
		return student;
	}
	//_csrf_token
	@GetMapping
	public CsrfToken getCsrfToken(HttpServletRequest request) {
		return (CsrfToken)request.getAttribute("_csrf");
	}
	
	
	
	
	
	
	
	
	
	
	
}
