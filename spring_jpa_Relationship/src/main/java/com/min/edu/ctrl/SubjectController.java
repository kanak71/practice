package com.min.edu.ctrl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.min.edu.entity.Subject;
import com.min.edu.repo.SubjectRepository;

@RestController
@RequestMapping("/subject")
public class SubjectController {

	
	@Autowired
	private SubjectRepository repository;
	
	@GetMapping
	public List<Subject> getSubject() {
		return repository.findAll();
	}
	
	@PostMapping
	public Subject createSubject(@RequestBody Subject subject) {
		return repository.save(subject);
	}
}
