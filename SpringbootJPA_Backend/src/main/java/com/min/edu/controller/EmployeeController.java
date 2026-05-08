package com.min.edu.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.ResourceAccessException;

import com.min.edu.model.Employee;
import com.min.edu.repository.EmployeeRepository;

import jakarta.transaction.Transactional;

//TODO 004 기능 요청을 위한 EmployeeController
@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "http://localhost:5173")
public class EmployeeController {
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	//직원전체조회
	@GetMapping("/employees")
	public List<Employee> getAllEmployees(){
		return employeeRepository.findAll();
	}
	
	//직원 입력
	@PostMapping("/employees")
	public Employee createEmployee(@RequestBody Employee employee) {
		return employeeRepository.save(employee);
	}
	
	//회원 ID로 조회하기
	@GetMapping("/employees/{id}")
	public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id){
		//orElseThrow() 찾은 값이 없다면 예외를 발생시킨다
		Employee employee = employeeRepository.findById(id)
					.orElseThrow(()-> new ResourceAccessException(id+"사원이 존재하지 않습니다"));
		
		//ResponseEntity객체를 보낼 때 사용하는 방법은?
		/*
		 * 1) new 방식
		 * 	new ResponseEntity<Employee>(employee,headers,HttpStatus.valueOf(200))
		 * 2) 정적방식
		 */
		
		return ResponseEntity.ok(employee);	//200인 ok상태일때 employee를 전달하겠다
		
		
	}
	@PatchMapping("/employees/{id}")
	@Transactional
	public ResponseEntity<Employee> updateEmployee(@PathVariable Long id,
													@RequestBody Employee inEmployee){
		//1) 주소로 전달받은 ID를 통해서 JPA를 실행하여 검색
		Employee employee = employeeRepository.findById(id)
							.orElseThrow(()-> new ResourceAccessException(id+"사원이 존재하지 않습니다"));
		//2) 화면에서 입력받은 값을 검색된 객체에 새로 입력
		// 문자열 객체를 효율적으로 사용하기 위한 라이브러리(commons-lang3, guava..)를 통해서 판단
		//commons-lang3 > isNotBlank : 값이 없는 것("",null," ")를 판별하여 false로 만들어준다
		if(StringUtils.isNotBlank(inEmployee.getFirstName())) {
			employee.setFirstName(inEmployee.getFirstName());
		}
		
		if(StringUtils.isNotBlank(inEmployee.getLastName())) {
			employee.setLastName(inEmployee.getLastName());
		}
		
		if(StringUtils.isNotBlank(inEmployee.getEmailId())) {
			employee.setEmailId(inEmployee.getEmailId());
		}
		
		return ResponseEntity.ok(employee);
		
	}
	
	//회원 정보 삭제하기
	@DeleteMapping("/employees/{id}")
	public ResponseEntity<Map<String, Boolean>> deleteEmployee(@PathVariable Long id){
		Employee employee = employeeRepository.findById(id)
								.orElseThrow(()-> new ResourceAccessException(id+"사원이 존재하지 않습니다"));
		
		//JPA는 WHERE절의 식별자로 삭제하는 것이 아니라 조회된 객체를 삭제한다
		employeeRepository.delete(employee);
		
		Map<String, Boolean> response = new HashMap<String, Boolean>();
		response.put("deleted", Boolean.TRUE);
		
		return ResponseEntity.ok(response);
	}
	
	
	
}
