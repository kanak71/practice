package com.min.edu.ctrl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.min.edu.StudentRepository;
import com.min.edu.entity.StudentEntity;
import com.min.edu.service.StudentService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

//TODO 004 JPA를 사용하는 Repository를 실행하여 결과를 값으로 반환하는 컨트롤러
@RestController
@RequestMapping("/student/app")
@Slf4j
public class StudentCtrl {
	
	//TODO 005 JPA의 기본메소드는 findALL을 통해서 DB에 모든 정보를 조회해보겠다
	@Autowired
	private StudentRepository repository;
	@GetMapping
	public List<StudentEntity> getAllStudent(){
		log.info("findAll JPA의 기본 메소드이다");
		return repository.findAll();
	}
	
	//TODO 011 새로운 Student 값을 입력하기 위한 POST 요청
	//		@RequestBody를 사용하여 화면에서 JSON의 값을 

	
	@Autowired
	private StudentService service;
	
	@PostMapping
	public StudentEntity addNewStudent(@RequestBody StudentEntity studentEntity) {
		log.info("@RequestBody를 통해서 JSON으로 값을 전달받는다");
//		return repository.save(studentEntity);
		return service.addNewStudent(studentEntity);
	}
	
	//TODO 016 주소 (@PathVariable)을 통해서 id를 추출하여 해당 row 삭제 진ㅅ행
	//localhost:8080/student/app/1 => 1인 id의 row가 삭제됨
	//ResponseEntity는 객체인데(데이터 + 상태(header))정보를 포함해서 보낼 때 사용한다
	//Map/String은 성공여부만 확인이 가능한 반면 ResponseEntity는 상황에 따라서 status 코드를 작성해서 보내줄 수 있다 => AJAX에서 상태를 명확하게 처리 가능하다
	@DeleteMapping(path="{studentid}")
	public ResponseEntity<Map<String,Object>> deleteStudent(@PathVariable Long studentid){
		StudentEntity dto = service.deleteStudent(studentid);
		
		//반환되는 JSON 데이터를 생성, Gson / Simple-json / jackson-bind(Spring에서 기본으로 사용하는 라이브러리 - Spring web)
		Map<String, Object> response = new HashMap<String, Object>();
		response.put("status", HttpStatus.OK.value());	//200이구나
		response.put("student", dto);	//뭐가 삭제됐는지 보내준다
		
		
		return new ResponseEntity(response, HttpStatus.OK);
	}
	
	//TODO 018 StudentEntity 정보를 업데이트
	//		영속성 문제와 Dirty Checking을 통한 처리
	@PutMapping(path = "{studentid}")
	public ResponseEntity<Map<String, Object>> updateStudent(@PathVariable Long studentid,			//수정 객체를 조회
															@RequestParam(required = false) String name,	//name 전달되었다면 수정
															@RequestParam(required = false) String email	//email 전달되었다면 수정
													){
		StudentEntity dto = service.updateStudent(studentid, name, email);
		
		Map<String, Object> response = new HashMap<String, Object>();
		response.put("message", "수정완료");
		response.put("status", HttpStatus.OK.value());
		response.put("student", dto);
		
		
		return new ResponseEntity(response, HttpStatus.OK);
		
	}
	
	
	

}
