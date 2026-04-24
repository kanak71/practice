package com.min.edu.ctrl;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.min.edu.model.service.IBoardService;
import com.min.edu.vo.BoardVo;
import com.min.edu.vo.UserVo;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


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
	
	//TODO 043 글작성 화면 이동 insertBoard.do
	@GetMapping(value="/insertBoard.do")
	public String insertBoard() {
		log.info("BoardController insertBoard.do GET 글작성화면 이동");
		return "insertBoard";
	}
	
	//TODO 046 글작성 입력 inserBoard.do
	/*
	 * 글작성 입력 => Parameter는 UserVo를 사용 => myBatis (selectKey를 통해서 새로 생성된 seq 전달)
	 * -> 상세보기 화면으로 이동(+생성된 seq값을 사용해서)
	 */
	@PostMapping(value="/insertBoard.do")
	public String insertBoard(BoardVo vo, HttpSession session) {
		log.info("BoardController insertBoard.do POST 글작성 입력 : {}", vo);
		String id = ((UserVo)session.getAttribute("loginVo")).getId();
		vo.setId(id);
		
		int cnt = service.wirteBoard(vo);
		log.info("글작성 성공 여부 : {}", (cnt>0)?"입력성공":"실패");
		
		//요청이 없으면 403
		//화면이 없으면 404
		//요청이 잘못 처리되면 500
		//화면에 값이 없으면 505
		//성공을 하면 200
		return (cnt>0) ? "redirect:/detailBoard.do?seq="+vo.getSeq() : "redirect:/logout.do";
	}
	
	//TODO 047 글상세 정보 detailBoard.do
	@GetMapping(value="/detailBoard.do")
	public String detailBoard(int seq, Model model) {
		log.info("BoardController detailBoard.do GET 상세글보기 : {} ", seq);
		
		BoardVo boardVo = service.getOneBoard(seq);
		
		model.addAttribute("boardOne", boardVo);
		
		return "detailBoard";
	}
	
	//TODO 053 다중삭제 및 삭제글에서 삭제 요청 multiDelete.do
	@RequestMapping(value="/multiDelete.do", method = {RequestMethod.GET, RequestMethod.POST})
	public String multiDelete(@RequestParam List<Integer> chkVal) {
		log.info("BoardController multiDelete.do GET|POST 다중삭제|단일삭제 : {}", chkVal);
		
		int n = service.delflagBoard(chkVal);
		log.info("삭제된 결과 : {}", n);
		
		return "redirect:/boardList.do";
	}
	
	//TODO 058 답글 입력 작성 replyBoard.do
	@GetMapping(value="/replyBoard.do")
	public String replyBoard(int seq, Model model) {
		log.info("BoardController replyBoard.do GET 답글 작업 화면 입력 : {}", seq);
		BoardVo boardVo = service.getOneBoard(seq);
		
		model.addAttribute("boardOne", boardVo);
		
		return "replyInsert";
		
	}
	
	//TODO 061 답글 입력 /replyBoard.do
	@PostMapping(value = "/replyBoard.do")
	public String replyBoard(BoardVo vo, HttpSession session) {
		log.info("BoardController /replyBoard.do POST 답글 입력 : {}", vo);
		
		String id = ((UserVo)session.getAttribute("loginVo")).getId();
		vo.setId(id);
		
		int n = service.reply(vo);
		log.info("답글 작성 성공 여부 : {}", n);
		
		return (n>0)?"redirect:/boardList.do":"redirect:/replyBoard.do?seq="+vo.getSeq();
	}
	
	//TODO 063 글삭제 복구 리스트 화면 restoreBoard.do
	@GetMapping(value="/restoreBoard.do")
	public String restoreBoard(Model model) {
		log.info("BoardController restoreBoard.do GET 삭제글 리스트 화면");
		
		List<BoardVo> restoreList = service.restoreBoard();
		model.addAttribute("restoreList", restoreList);
		
		return "restoreBoard";
		
	}
	
	//TODO 066 삭제글 복구 AJAX restore.do
	/*
	 * 일반 Controller에 값만을 전달하는 @ResponseBody를 사용
	 *  ㄴ @Controller + @ResponseBody의 집합체를 만들고싶다면 @RestController로 작업하면 된다
	 *  ㄴ HttpServletResponse response
	 *  	response.getWrite().print()
	 */
	
	@ResponseBody
	@GetMapping(value="/restore.do")
	public String restore(@RequestParam List<Integer> seq) {
		log.info("BoardController restore.do GET @ResponseBody 삭제 글 복구 : {}", seq);
		
		int n = service.restoreDelflag(seq);
		
		//화면 요청이 아니라 값 전달 : SPA(Single Page Application)
		return (n>0)?"true":"false";
		
	}
	
	
}


















