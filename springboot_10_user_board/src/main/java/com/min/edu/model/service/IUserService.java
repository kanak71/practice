package com.min.edu.model.service;

import java.util.List;
import java.util.Map;

import com.min.edu.vo.UserVo;

//TODO 008 User 기능 정의를 위한 Interface
public interface IUserService {

	// 로그인
    UserVo getLogin(Map<String, Object> map);

    // 아이디 중복 체크
    int isDuplicateCheck(String id);

    // 회원가입
    int signupMember(UserVo vo);

    // 일반 사용자 조회 (AUTH = 'U')
    List<UserVo> userSelectAll();

    // 전체 사용자 조회
    List<UserVo> getAllUser();

    // 사용자 검색
    List<UserVo> getSearcherUser(Map<String, Object> map);

    // 아이디 찾기
    String findId(Map<String, Object> map);

    // 권한 변경
    int setChangeAuth(Map<String, Object> map);
}
