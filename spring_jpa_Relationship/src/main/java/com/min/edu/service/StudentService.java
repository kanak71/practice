package com.min.edu.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.min.edu.dto.StudentDto;
import com.min.edu.dto.SubjectDto;
import com.min.edu.entity.Student;
import com.min.edu.repo.StudentRepository;

import jakarta.transaction.Transactional;

//TODO 007 프로덕션 DTO에 student를 조회했을 경우 값을 담아주는 Service
@Service
public class StudentService {
	
	@Autowired
	private StudentRepository studentRepository;
	
	//TODO 008 Convert Entity to DTO
	public StudentDto convertToStudentDto(Student student) {
		List<SubjectDto> subjectDtos = student.getSubjects().stream()
									.map(subject -> new SubjectDto(subject.getId(), subject.getTitle()))
									.collect(Collectors.toList());
		
		return new StudentDto(student.getId(), student.getName(), subjectDtos);
		}
	
	public List<StudentDto> getAllStudents(){
		return studentRepository.findAll().stream()
					.map(this :: convertToStudentDto)
					.collect(Collectors.toList());
	}
	
	//TODO 102 Student에 Subject를 포함하기 위한 프로덕션
	public StudentDto getOneStudent(Long studentId) {
		Student student = studentRepository.findById(studentId)
				.orElseThrow(()->new IllegalStateException("학생 ID가 존재하지 않습니다"));
		return convertToStudentDto(student);
	}
	
	//TODO 104 StudentId와 name을 입력받아 값을 변경한다
	//		영속성, 조회된 결과 객체를 변경하면 자동으로 반영된다
	@Transactional	//이 Annotation이 핵심이었다
	public StudentDto updateStudentName(Long studentId, String name) {
		Student student = studentRepository.findById(studentId)
				.orElseThrow(()-> new IllegalStateException("학생이 존재하지 않습니다"));
		student.setName(name);	//더디 채킹에 의해서 자동으로 업데이트
		
		// @Transactional를 통해서 같은 트랜젝션 내이기 때문에 student를 안전하게 읽어올 수 있다
		return convertToStudentDto(student);
	}

}
