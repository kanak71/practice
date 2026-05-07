package com.min.edu.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.min.edu.model.Employee;
import com.min.edu.repository.EmployeeRepository;

//TODO 004 기능 요청을 위한 EmployeeController
@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "http://127.0.0.1:3000")
public class EmployeeController {
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	//직원전체조회
	@GetMapping("/employees")
	public List<Employee> getAllEmployees(){
		return employeeRepository.findAll();
	}
}
