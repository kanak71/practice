package com.min.edu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.min.edu.model.Employee;
import com.min.edu.repository.EmployeeRepository;

@RestController
@RequestMapping("/api/page")
@CrossOrigin("*")
public class PageController {
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	@GetMapping
	public Page<Employee> getPosts(	//클라이언트가 전송한 ?page=0&size=5&sort=id,desc
			//	통해서 findAll에 넣어주면 =>
			/*
			 * Page가 가지고 있는 값
			 * Page<Employee> 포함하고 있는 값
			 * content	//현재 페이지의 데이터
			 * totalElement	//전체 데이터의 개수
			 * totalPage	//전체 페이지의 개수
			 * size			//페이지의 크기
			 * number		//현재 페이지의 번호
			 * first		//첫 페이지 여부
			 * last			//마지막 페이지 여부
			 * empty		//비어있는지
			 * sort			
			 */
			@PageableDefault(size=5, sort="id", direction=Sort.Direction.DESC) Pageable pageable
			){
		
		return employeeRepository.findAll(pageable);
	}

}
