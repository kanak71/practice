package com.min.edu.model.service;

import java.util.List;

import com.min.edu.vo.BoardVo;

//TODO 008 Board 기능 정의를 위한 Interface
public interface IBoardService {

	// 게시글 리스트 조회
    List<BoardVo> userBoardList();

    // 게시글 삭제 (DELFLAG = 'Y')
    int delflagBoard(List<Integer> seqs);

    // 게시글 작성
    int wirteBoard(BoardVo vo);

    // 게시글 상세조회
    BoardVo getOneBoard(int seq);

    // 삭제된 게시글 조회
    List<BoardVo> restoreBoard();

    // 게시글 복구 (DELFLAG = 'N')
    int restoreDelflag(List<Integer> seq);
    
    //답글 작성 Transaction 처리
    public int reply(BoardVo vo);
}
