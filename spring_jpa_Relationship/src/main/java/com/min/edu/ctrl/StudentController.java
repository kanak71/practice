package com.min.edu.ctrl;

import java.nio.channels.IllegalSelectorException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
	
	//TODO 101 API시나리오 특정 학생 조회
	@GetMapping(path = "{studentId}")
	public ResponseEntity<?> getOneStudent(@PathVariable Long studentId){
		try {
			StudentDto studentDto = service.getOneStudent(studentId);
			return ResponseEntity.ok(studentDto);
		} catch (IllegalStateException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
			
		}
	}
	
	//TODO 103 학생 정보 수정 더티 체킹
	@PutMapping(path="{studentId}")
	public ResponseEntity<?> modifyStudent(@PathVariable Long studentId, @RequestParam String name){
		try {
			StudentDto updated = service.updateStudentName(studentId, name);
			return ResponseEntity.ok(updated);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}
	
	//TODO 105 특정 학생(ID)를 삭제한다 - 연결되어있는(enrolled)를 수강과목을 삭제
	@DeleteMapping(path = "{studentId}")
	public ResponseEntity<?> deleteStudent(@PathVariable Long studentId){
		//학생 정보를 삭제하기 위해서 수강정보를 삭제 해야한다
		Student student = repository.findById(studentId)
				.orElseThrow(()-> new IllegalStateException("학생 ID가 존재하지 않습니다"));
		//학생과 관련된 모든 수강정보를 제거
		student.getSubjects().forEach(subject -> subject.getEnrolledStudent().remove(student));
		//학생을 삭제
		repository.deleteById(studentId);
		
		return ResponseEntity.ok().body(Map.of("message", "Student successfully deleted"));
	}
	
}
