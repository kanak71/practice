package com.min.edu.ctrl;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.min.edu.model.service.IBoardService;
import com.min.edu.vo.BoardVo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

//TODO 018 Answerboard 관련 요청 Controller
@Controller
@Slf4j
@RequiredArgsConstructor
public class BoardController {
	
	private final IBoardService service;
	
	//TODO 019 /board.do 요청 후 boardList.html 화면으로 이동
	@GetMapping(value="/boardList.do")
	public String boardList(Model model) {
		log.info("BoardController GET 전체조회");
		
		/*캐쉬 삭제 코드 작성 */
		
		List<BoardVo> lists = service.userBoardList();
		model.addAttribute("boardList", lists);
		
		return "boardList";
	}
}
