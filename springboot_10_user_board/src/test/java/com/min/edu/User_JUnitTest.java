package com.min.edu;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.min.edu.model.mapper.IUserDao;
import com.min.edu.vo.UserVo;

//TODO 010 User 기능 테스트

@SpringBootTest
class User_JUnitTest {
	
	@Autowired
	private IUserDao dao;
		

	@Test
	void test() {
		//1) 로그인
		UserVo loginVo = dao.getLogin(new HashMap<String, Object>(){{
																put("id", "user01");
																put("password", "1111");
		}});
		
		assertNotNull(loginVo);
		
		//2) 아이디 중복 검사
		int idCheck = dao.isDuplicateCheck("user01");
		assertEquals(1, idCheck);
		
		//3) 회원가입 : id는 자동생성된다
//		UserVo inVo = UserVo.builder()
//						.name("둘리")
//						.password("1111")
//						.email("test@test.com")
//						.build();
//		
//		int signupCheck = dao.signupMember(inVo);
//		assertEquals(1, signupCheck);
		
		//4) 회원 전체 조회
		List<UserVo> userList = dao.userSelectAll();
		assertNotEquals(0, userList.size());
		
		//5) 회원 검색 : opt(id, name) keyword(like문)
		List<UserVo> searchList = dao.getSearcherUser(new HashMap<String, Object>(){{
																					put("opt", "name");
																					put("keyword", "사용자");
		}});
		assertNotEquals(0, searchList.size());
		
		//6) 아이디 찾기
		String id = dao.findId(new HashMap<String, Object>(){{
															put("name", "홍길동");
															put("email", "example@test.com");
		}});
		
		assertNotNull(id);
		
	}

}





















