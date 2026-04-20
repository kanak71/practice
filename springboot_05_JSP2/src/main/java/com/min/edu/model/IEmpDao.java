package com.min.edu.model;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.min.edu.dto.EmpVo;

//TODO 006 인터페이스로 선언되어 실행되는 DAO
/*
 * mapper xml의 namespace 설정에 따라서 위치와 클래스 작성
 * @Mapper로 선언하여 작성된 interface가 실행되어 진다
 * 메소드명 - id(getAllEmp)가 된다
 * 클래스 명이 = namespace(IEmpDao)가 된다
 */
@Mapper
public interface IEmpDao {
	public List<EmpVo> getAllEmp();
}
