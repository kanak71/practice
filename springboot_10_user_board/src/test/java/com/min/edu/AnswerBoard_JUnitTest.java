package com.min.edu;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.Test;

import com.min.edu.model.mapper.IBoardDao;
import com.min.edu.model.service.IBoardService;
import com.min.edu.vo.BoardVo;


//TODO 010 AnswerBoard의 기능은 IBoard, IBoardService의 JUnit test
/*
 * JUnit Test는 단위 테스트 도구이다
 * 다른거와 연결을 할 수 없고 연결된 객체만을 사용할 수 있다
 * 근데 SpringBoot는 Spring Container가 관리 => Spring Container의 ApplicationContext를 접근할 수 있어야 한다
 * @SpringBootTest 사용해서 JUnit을 처리해 줘야 한다
 */

@SpringBootTest
class AnswerBoard_JUnitTest {

	@Autowired
	private IBoardDao dao;
	
//	@Autowired
//	privateIBoardService service;
	
	@Test
	public void boardTest() {
		//1) 전체조회 테스트
		List<BoardVo> lists = dao.userBoardList();
		assertNotEquals(0, lists.size());
		
		//2) 새글입력
		BoardVo inVo = BoardVo.builder()
				.id("user01")
				.title("글작성 제목 테스트")
				.content("글내용 작성 테스트")
				.build();
		
		int writeCnt = dao.wirteBoard(inVo);
		assertEquals(1, writeCnt);
		
		//3) 상세조회
		BoardVo oneVo = dao.getOneBoard(2);
		assertNotNull(oneVo);
		
		//4) 다중삭제
//		int deleteCheck = dao.delflagBoard(List.of(119, 120));
//		assertEquals(2, deleteCheck);
		
		//5) 복구리스트
		List<BoardVo> restoreList = dao.restoreBoard();
		assertNotEquals(0, restoreList.size());
		
		//6) 복구
		int restoreDelflageCheck = dao.restoreDelflag(List.of(119, 120));
		assertEquals(2, restoreDelflageCheck);
		
		//7) 답글 => Service에서 Transaction 처리
		
	}
}










