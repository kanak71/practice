package com.min.edu;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.apache.ibatis.mapping.MappedStatement;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;


//TODO 009 Bean 테스트, DataSource
/* 1) Datasource => application.properties에서 작성한 값을 가지고 Bean으로 만들어 준다
 * 2) mybatis -> SqlSessionFactory => Spring Boot => SqlSessionTemplate
 *  	테스트 : 매퍼등록여부, 인터페이스명+메소드(id)
 */
@SpringBootTest
class Springboot10UserBoardApplicationTests {

	//모든 SpringBean 관리하는 객체 : ApplicationContext
	
	@Autowired
	private ApplicationContext context;
	
	//SpringBoot 관리 하는 myBatis : SqlSessionTemplate
	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;
	
	/*
	 * 객체는 Ioc에 의해서 Bean으로 SpringBoot가 가지고 ㅣㅇㅆ고
	 * 실행 방법은 같다
	 */
	@Test
	void bean_test() {
		//bean 타입기반이다. DataSource 객체는 호출(이름) camel Case 방법으로 호출 => "dataSource"
		DataSource dataSource = context.getBean("dataSource", DataSource.class);
		
		//Persistence layer는 JDBC를 쉽게 사용하기 위해서 만들어진 객체(myBatis, ORM-JPA)
		// JDBC 6단계 : 1단계 springBoot가 해준다 => 2345단계..
		
		try {
			Connection conn = dataSource.getConnection();
			//application.properties의 정보를 출력
			System.out.println("DataSource URL :" +conn.getMetaData().getURL());
			System.out.println("DataSource UserName :" + conn.getMetaData().getUserName());
			System.out.println("DataSource DriverName :"  +conn.getMetaData().getDriverName());
			
			//SQL 실행을 하지 않고 연결 Mapper 파일 등록 확인
			boolean mapperCheck = sqlSessionTemplate.getConfiguration().hasMapper(com.min.edu.model.mapper.IBoardDao.class);
			assertTrue(mapperCheck);
			
			//Mapper의 쿼리문(Statement) 확인
			MappedStatement statementCheck = sqlSessionTemplate.getConfiguration().getMappedStatement("com.min.edu.model.mapper.IBoardDao.userBoardList");
			assertNotNull(statementCheck);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

}










