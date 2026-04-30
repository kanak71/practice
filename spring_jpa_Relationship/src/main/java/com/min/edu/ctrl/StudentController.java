package com.min.edu.ctrl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.min.edu.dto.StudentDto;
import com.min.edu.entity.Student;
import com.min.edu.repo.StudentRepository;
import com.min.edu.service.StudentService;

@RestController
@RequestMapping("/student")
public class StudentController {

	@Autowired
	private StudentRepository repository;
	
	@Autowired
	private StudentService service;
	
	@GetMapping
	public List<Student> getStudent(){
		return repository.findAll();
	}
	
	@PostMapping
	public Student createStudent(@RequestBody Student student) {
		return repository.save(student);
	}
	
	//TODO 009 프로덕션 DTO를 통한 학생+과목 조회
	@GetMapping(path= "/all")
	public List<StudentDto> getAllStudent(){
		return service.getAllStudents();
	}
	
}
