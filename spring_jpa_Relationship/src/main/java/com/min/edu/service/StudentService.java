package com.min.edu.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.min.edu.dto.StudentDto;
import com.min.edu.dto.SubjectDto;
import com.min.edu.entity.Student;
import com.min.edu.repo.StudentRepository;

//TODO 007 프로덕션 DTO에 student를 조회했을 경우 값을 담아주는 Service
@Service
public class StudentService {
	
	@Autowired
	private StudentRepository studentRepository;
	
	//TODO 008 Convert Entity to DTO
	public StudentDto convertToStudentDto(Student student) {
		List<SubjectDto> subjectDtos = student.getSebjects().stream()
									.map(subject -> new SubjectDto(subject.getId(), subject.getTitle()))
									.collect(Collectors.toList());
		
		return new StudentDto(student.getId(), student.getName(), subjectDtos);
		}
	
	public List<StudentDto> getAllStudents(){
		return studentRepository.findAll().stream()
					.map(this :: convertToStudentDto)
					.collect(Collectors.toList());
	}

}
