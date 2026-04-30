package com.min.edu.service;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.min.edu.StudentRepository;
import com.min.edu.entity.StudentEntity;

import jakarta.transaction.Transactional;

//TODO 013 StudentRepository와 연결하여 사용하는 StudentService
@Service
public class StudentService {
	
	//TODO 014 StudentRepository 주입
	@Autowired
	private StudentRepository repository;
	
	//TODO 015 email을 판단하여 예외 혹은 입력 기능의 service
	public StudentEntity addNewStudent(StudentEntity studentEntity) {
		Optional<StudentEntity> studentOptional = repository.findStudentByEmail(studentEntity.getEmail());
		//중복되는 이메일을 확인해서 저장이 되지 않도록 한다
		//중복확인(isPresent() -> true -> throw 발생)
		if(studentOptional.isPresent()) {
			throw new IllegalArgumentException("email이 중복됩니다");
		}
		return repository.save(studentEntity);
	}
	
	//TODO 017 id를 확인하여 예외 혹은 삭제 기능의 service
	// orElseThrow 사용 판단도 해주고 자동으로 예외 던져줌
	public StudentEntity deleteStudent(Long studentid){
		StudentEntity student = repository.findById(studentid).orElseThrow(()-> new IllegalStateException("학생 아이디가 존재하지 않습니다"));
		repository.deleteById(studentid);
		return student;
	}
	
	//TODO 019 @Transaction을 통해서 Dirty Checking이 가능하도록 한다
	@Transactional
	public StudentEntity updateStudent(Long studentid, String name, String email) {
		System.out.println("전달받은 name 값 : " + name);
		System.out.println("전달받은 email 값 : " + email);
		
		StudentEntity student = repository.findById(studentid).orElseThrow(()->new IllegalStateException("해당"+studentid+"의 학생은 없습니다"));
		
		if(name != null && name.length() >0 && !Objects.equals(student.getName(), name)) {
			student.setName(name); 		//자동으로 update가 실행된다
		}
		
		if(email != null && email.length() >0 && !Objects.equals(student.getEmail(), email)) {
			//이메일 유효성 검사(조회된 id의 email말고 다른 정보의 이메일도 확인
			Optional<StudentEntity> studentOptional = repository.findStudentByEmail(email);
			if(studentOptional.isPresent()) {
				throw new IllegalStateException("존재하는 이메일입니다");
			}
			student.setEmail(email);
			
		}
		
		return student;
	}
}








