package com.min.edu.ctrl;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.catalina.connector.Response;
import org.apache.ibatis.annotations.Delete;
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

import com.min.edu.dto.SubjectDto;
import com.min.edu.dto.TeacherDto;
import com.min.edu.entity.Subject;
import com.min.edu.entity.Teacher;
import com.min.edu.repo.SubjectRepository;
import com.min.edu.repo.TeacherRepository;

@RestController
@RequestMapping("/teacher")
public class TeacherController {

	
	@Autowired
	private TeacherRepository repository;
	
	@Autowired
	private SubjectRepository subjectRepository;
	
	@GetMapping
	public List<Teacher> getTeacher(){
		return repository.findAll();
	}
	
	@PostMapping
	public Teacher createTeacher(@RequestBody Teacher teacher) {
		return repository.save(teacher);
	}
	
	//TODO 301 특정 교수(ID)조회
	@GetMapping(path = "/{teacherId}")
	public Teacher getOneTeacher(@PathVariable Long teacherId) {
		Teacher teacher = repository.findById(teacherId)
				.orElseThrow(()-> new IllegalStateException("조회된 교수가 없습니다"));
		return teacher;
	}
	
	//TODO 303 특정 교수(ID)의 담당과목을 조회
	@GetMapping(path = "/{id}/subjects")
	public ResponseEntity<?> getSubjectByTeacherId(@PathVariable Long id){
		//교수조회
		Teacher teacher = repository.findById(id).orElse(null);
		if(teacher != null){
			//과목 정보를 DTO로 변환
			List<SubjectDto> subjectDtos = teacher.getSubjects().stream()
					.map(subject -> new SubjectDto(subject.getId(), subject.getTitle()))
					.collect(Collectors.toList());
			
			//TeacherDto의 프로덕션에 값을 담아 줌
			TeacherDto teacherDto = new TeacherDto(teacher.getId(), teacher.getName(), subjectDtos);
			
			return ResponseEntity.ok(teacherDto);
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	
	//TODO 304 교수 정보수정
	@PutMapping(path = "/{teacherId}")
	public ResponseEntity<?> modifyTeacher(@PathVariable Long teacherId,
											@RequestParam String name){
		Teacher teacher = repository.findById(teacherId).orElseGet(null);
		
		if(teacher != null) {
			teacher.setName(name);
			repository.save(teacher);
			return ResponseEntity.ok(teacher);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("과목이 존재하지 않습니다");
		}
	}
	
	//TODO 305 특정 교수(ID)가 담당하는 특정 과목을 해제한다
	@DeleteMapping(path = "/{teacherId}/subjects/{subjectId}")
	public ResponseEntity<?> removeSubjectFromTeacher(@PathVariable Long teacherId,
														@PathVariable Long subjectId){
		//1. 교수 조회
		Teacher teacher = repository.findById(teacherId)
							.orElseThrow(()-> new IllegalStateException("교수가 존재하지 않습니다"));
		//2. 과목 조회 및 존재 여부 확인
		Subject subjectToRemove = teacher.getSubjects().stream()
									.filter(subject -> subject.getId().equals(subjectId))
									.findFirst()
									.orElseThrow(()-> new IllegalStateException("과목이 교수와 연결되어 있지 않습니다"));
		
		//3. 찾은 과목을 통해서 teacher에서 삭제
		teacher.getSubjects().remove(subjectToRemove);
		//4. 과목은 teacher_id로 FK관계가 되어 있다 따라서 교수 관계를 해제
		subjectToRemove.setTeacher(null);
		
		//5. 변경사항을 저장
		repository.save(teacher);
		
		return ResponseEntity.ok(Map.of("message","과목이 교수로부터 성공적으로 삭제되었습니다"));
	}
	
	//TODO 306 특정 교수(ID)가 담당하는 모든 과목 해제 및 삭제
	@DeleteMapping(path = "/{teacherId}/subjects")
	public ResponseEntity<?> removeAndDeleteAllSubjectsFromTeacher(@PathVariable Long teacherId){
		//교수조회
		Teacher teacher = repository.findById(teacherId)
								.orElseThrow(()-> new IllegalStateException("해당 교수가 존재하지 않습니다"));
		
		//1안 과목과 교소 해제
		Set<Subject> subjects = teacher.getSubjects();
		for (Subject subject : subjects) {
			subject.setTeacher(null);
		}
		subjects.clear(); 	//subject에서 교수와의 완계 해제
		
		//2안 과목 데이터까지 삭제
		Set<Subject> subjects2 = teacher.getSubjects();
		for (Subject subject : subjects2) {
			//해당 subject를 Repository를 통해서 객체를 delete를 통해서 삭제
			subjectRepository.delete(subject);
		}
		
		repository.save(teacher);
		
		return ResponseEntity.ok(Map.of("message", "교수의 모든 과목이 삭제되었습니다"));
		
	}
	
	//TODO 307 특정 교수(ID)에 한개 과목 연결
	@PostMapping(path = "/{teacherId}/subjects/{subjectId}")
	public ResponseEntity<?> addSubjectsToTeacher(@PathVariable Long teacherId, @PathVariable Long subjectId){
		//교수조회
		Teacher teacher = repository.findById(teacherId)
							.orElseThrow(()-> new IllegalStateException("해당 교수가 존재하지 않습니다"));
		
		//과목조회
		Subject subject = subjectRepository.findById(subjectId)
							.orElseThrow(()-> new IllegalStateException("해당 과목이 존재하지 않습니다"));
		
		//과목에 교수 설정
		subject.setTeacher(teacher);
		
		//저장
		subjectRepository.save(subject);
		
		return ResponseEntity.ok(Map.of("message","과목이 교수에 성공적으로 추가되었습니다"));
	}
	
	//TODO 308 특정 교수에 여러 과목을 연결한다
	@PostMapping(path = "/{teacherId}/subjects")
	public ResponseEntity<?> addMultiSubjectToTeacher(@PathVariable Long teacherId, @RequestBody Map<String, List<Long>> responseBody){
		//요청 본문(body)에서 subjects를 가져온다
		List<Long> subjects = responseBody.get("subjectId");
		if(subjects == null || subjects.isEmpty()) {
			return ResponseEntity.badRequest().body(Map.of("message","과목 ID 목록이 비어있습니다"));
		}
		
		//교수를 조회
		Teacher teacher = repository.findById(teacherId)
				.orElseThrow(()-> new IllegalStateException("해당 교수가 존재하지 않습니다"));
		//존재하는 과목 ID만 필터링 
		List<Subject> validateSubjects = subjectRepository.findAllById(subjects);
		Set<Long> validateSubjectIds = validateSubjects.stream()
									.map(Subject :: getId)
									.collect(Collectors.toSet());
		
		//존재하지 않는 ID 확인
		List<Long> validIds = subjects.stream()
									.filter(id -> !validateSubjectIds.contains(id))
									.collect(Collectors.toList());
		
		//없는 과목을 연결하는 상황이 발생하기 전에 예외 처리해서 사용자에게 알려줌
		if(!validIds.isEmpty()) {
			return ResponseEntity.badRequest().body(Map.of("message","다음 과목 ID가 존재하지 않습니다:"+validIds, "invalidIds", validIds));
		}
		
		//유효한 과목을 교수에 연결
		validateSubjects.forEach(subject -> subject.setTeacher(teacher));
		subjectRepository.saveAll(validateSubjects);
		
		return ResponseEntity.ok(Map.of("message","여러 과목이 교수에서 성공적으로 연결되었습니다"));
	}
	
	//TODO 309 특정 교수(ID)삭제
	@DeleteMapping(path = "/{teacherId}")
	public ResponseEntity<?> deleteTeacher(@PathVariable Long teacherId){
		//교수조회
		Teacher teacher = repository.findById(teacherId)
				.orElseThrow(()-> new IllegalStateException("해당 교수가 존재하지 않습니다"));
		//조회된 교수의 subject, 해당 subject에서 teacher를 null로 변경
		Set<Subject> subjects = teacher.getSubjects();
		for(Subject subject : subjects) {
			subject.setTeacher(null);	//과목(subject)의 교수 연결 해제
		}
		subjectRepository.saveAll(subjects);	//연결 해제 후 과목 저장
		
		//교수삭제
		repository.deleteById(teacherId);
		
		return ResponseEntity.ok(Map.of("message","교수가 성공적으로 삭제되었습니다"));
	}
	
}





