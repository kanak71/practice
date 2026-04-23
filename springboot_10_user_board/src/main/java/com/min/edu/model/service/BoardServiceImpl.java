package com.min.edu.model.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.min.edu.model.mapper.IBoardDao;
import com.min.edu.vo.BoardVo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

//TODO 012 AnswerBoard Service 구현
@Service
@RequiredArgsConstructor
@Slf4j
public class BoardServiceImpl implements IBoardService {

	private final IBoardDao dao;
	
	@Override
	public List<BoardVo> userBoardList() {
		log.info("BoardServiceImpl {}", "userBoardList");
		return dao.userBoardList();
	}

	@Override
	public int delflagBoard(List<Integer> seqs) {
		log.info("BoardServiceImpl {}", "delflagBoard");
		return dao.delflagBoard(seqs);
	}

	@Override
	public int wirteBoard(BoardVo vo) {
		log.info("BoardserviceImpl{}", "wirteBoard");
		return dao.wirteBoard(vo);
	}

	@Override
	public BoardVo getOneBoard(int seq) {
		log.info("BoardServiceImpl {}", "getOnBoard");
		return dao.getOneBoard(seq);
	}

	@Override
	public List<BoardVo> restoreBoard() {
		log.info("BoardServiceImpl {}", "restoreBoard");
		return dao.restoreBoard();
	}

	@Override
	public int restoreDelflag(List<Integer> seq) {
		log.info("BoardServiceImpl {}", "restoreDelflag");
		return dao.restoreDelflag(seq);
	}

	// Transaction : 답글 update+insert 동작 - All or nothing, ACID
	// @Transactional : 기본 (true) - insert, update, delete / true - select

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public int reply(BoardVo vo) {
		log.info("BoardServiceImpl {}", "reply");
		int n = dao.replyUpdate(vo);
		int m  = dao.replyInsert(vo);
		return (n+m)>0?1:0;
	}

}
