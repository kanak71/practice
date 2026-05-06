package com.min.edu.ctrl;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

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

import com.min.edu.entity.Student;
import com.min.edu.entity.Subject;
import com.min.edu.entity.Teacher;
import com.min.edu.repo.StudentRepository;
import com.min.edu.repo.SubjectRepository;
import com.min.edu.repo.TeacherRepository;

@RestController
@RequestMapping("/subject")
public class SubjectController {

	
	@Autowired
	private SubjectRepository repository;
	
	@Autowired
	private TeacherRepository teacherRepository;
	
	@Autowired
	private StudentRepository studentRepository;
	
	@GetMapping
	public List<Subject> getSubject() {
		return repository.findAll();
	}
	
	@PostMapping
	public Subject createSubject(@RequestBody Subject subject) {
		return repository.save(subject);
	}
	
	//TODO 201 특정 과목(ID)를 조회한다
	@GetMapping(path = "{subjectId}")
	public Subject getOneSubject(@PathVariable Long subjectId) {
		Subject subject = repository.findById(subjectId)
				.orElseThrow(()-> new IllegalStateException("과목 ID가 존재하지 않습니다"));
		return subject;
	}
	
	//TODO 202 과목에 교수를 연결
	@PutMapping("/{subjectId}/teacher/{teacherId}")
	public Subject assignTeacherToSubject(@PathVariable Long subjectId, @PathVariable Long teacherId) {
		//과목을 조회
		Subject subject = repository.findById(subjectId).get();
		//추가할 교수를 조회
		Teacher teacher = teacherRepository.findById(teacherId).get();
		
		//과목 엔터티에 교수를 추가한다
		subject.setTeacher(teacher);
		
		//저장한다
		return repository.save(subject);
	}
	
	//TODO 203 과목명을 수정한다
	@PutMapping(path = "/{subjectId}")
	public ResponseEntity<?> modifySubject(@PathVariable Long subjectId, @RequestParam String title){
		Subject subject = repository.findById(subjectId).orElseGet(null);
		if(subject!= null) {
			subject.setTitle(title);
			repository.save(subject);
			return ResponseEntity.ok(subject);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("과목이 존재하지 앖습니다");
		}
	}
	
	//TODO 204 특정 과목(ID) 삭제 한다
	@DeleteMapping(path = "/{subjectId}")
	public ResponseEntity<?> deleteSubject(@PathVariable Long subjectId){
		Subject subject = repository.findById(subjectId)
				.orElseThrow(()-> new IllegalStateException("해당 과목은 존재하지 않습니다"));
		
		//1) 연관된 학생 관계를 삭제
		Set<Student> enrolledStudents = subject.getEnrolledStudent();
		for (Student student : enrolledStudents) {
			student.getSubjects().remove(student); //학생 측에서도 관계를 해제
		}
		
		enrolledStudents.clear();	//subject 측에서도 관계를 해제
		
		//Teacher와의 관계도 해제
		subject.setTeacher(null);
		
		//변경사항을 저장
		repository.save(subject);
		
		//최종적으로 subject 삭제
		repository.delete(subject);
		
		return ResponseEntity.ok().body(Map.of("message","Subject Successfully deleted"));
		
	}
	
	//TODO 205 특정 과목(ID)에 학생을 연결
	@PutMapping(path = "/{subjectId}/student/{studentId}")
	public Subject addStudentToSubject(@PathVariable Long subjectId, @PathVariable Long studentId) {
		Subject subject = repository.findById(subjectId)
				.orElseThrow(()-> new NoSuchElementException("ID:"+subjectId+"에 해당 과목이 없습니다"));
		
		Student student = studentRepository.findById(studentId)
				.orElseThrow(()-> new NoSuchElementException("ID:"+studentId+"에 해당 학생이 없습니다"));
		
		//양방향 연관관계 편의 로직 (메모리상의 일관성을 유지하기 위해서 양쪽 모두 추가)
		subject.getEnrolledStudent().add(student);
		student.getSubjects().add(subject);	//Student 엔터티의 subjects 컬렉션에도 추가
		
		return repository.save(subject);
	}
	
}









