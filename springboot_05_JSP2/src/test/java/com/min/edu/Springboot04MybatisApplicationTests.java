package com.min.edu;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.min.edu.dto.EmpVo;
import com.min.edu.model.IEmpDao;
import com.min.edu.model.IEmpDaoInterface;

@SpringBootTest
class Springboot04MybatisApplicationTests {

	//TODO 010 xml mapper 테스트
//	@Autowired
	private IEmpDao empDaoXml;
	
//	@Test
	void xmlMyBatis() {
		
		List<EmpVo> list = empDaoXml.getAllEmp();
		
		assertNotNull(list,"조회 결과는 null이면 안된다");
		assertFalse(list.isEmpty(),"사원 목록은 비어있으면 안된다");
		
		EmpVo emp = list.get(0);
		assertNotNull(emp.getEmpno());
	}
	
	
	//TODO 012 interface + Annotation을 통한 mybatis 쿼리 실행
	@Autowired
	private IEmpDaoInterface empDaoInterface;
	
	@Test
	public void interfaceMyBatis() {
		List<EmpVo> list = empDaoInterface.selectBoardInterface();
		
		assertFalse(list.isEmpty(),"사원목록은 비어 있으면 안됩니다");
	}
	
	//TODO 014 interface + annotation의 Dynamic query 처리
	@Test
	public void inferfaceMyBatisDynamic() {
		List<EmpVo> list = empDaoInterface.selectEmpBySal("1000");
		
		assertFalse(list.isEmpty());
	}

}









