package com.min.edu.ctrl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.min.edu.entity.Teacher;
import com.min.edu.repo.TeacherRepository;

@RestController
@RequestMapping("/teacher")
public class TeacherController {

	
	@Autowired
	private TeacherRepository repository;
	
	@GetMapping
	public List<Teacher> getTeacher(){
		return repository.findAll();
	}
	
	@PostMapping
	public Teacher createTeacher(@RequestBody Teacher teacher) {
		return repository.save(teacher);
	}
}
