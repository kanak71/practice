package com.min.edu.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.min.edu.vo.BoardVo;

//TODO 007 Board Mapper xml로 부터 @Mapper Interface 작성
@Mapper
public interface IBoardDao {

	// 게시글 리스트 조회
    List<BoardVo> userBoardList();

    // 게시글 삭제 (DELFLAG = 'Y')
    int delflagBoard(List<Integer> seqs);

    // 게시글 작성
    int wirteBoard(BoardVo vo);

    // 게시글 상세조회
    BoardVo getOneBoard(int seq);

    // 답글 step 업데이트
    int replyUpdate(BoardVo vo);

    // 답글 입력
    int replyInsert(BoardVo vo);

    // 삭제된 게시글 조회
    List<BoardVo> restoreBoard();

    // 게시글 복구 (DELFLAG = 'N')
    int restoreDelflag(List<Integer> seq);
}