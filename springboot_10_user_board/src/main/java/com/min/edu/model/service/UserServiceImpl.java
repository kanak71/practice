package com.min.edu.model.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.min.edu.model.mapper.IUserDao;
import com.min.edu.vo.UserVo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

//TODO 013 User Service 구현
@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements IUserService {
	
	private final BoardServiceImpl boardServiceImpl;
	@Autowired
	private final IUserDao dao;

//	UserServiceImpl(BoardServiceImpl boardServiceImpl) {
//		this.boardServiceImpl = boardServiceImpl;
//	}

	@Override
	public UserVo getLogin(Map<String, Object> map) {
		log.info("UserboardServiceImpl {}", "getLogin");
		return dao.getLogin(map);
	}

	@Override
	public int isDuplicateCheck(String id) {
		log.info("UserboardServiceImpl {}", "isDuplicateCheck");
		return dao.isDuplicateCheck(id);
	}

	@Override
	public int signupMember(UserVo vo) {
		log.info("UserboardServiceImpl {}", "signupMember");
		return dao.signupMember(vo);
	}

	@Override
	public List<UserVo> userSelectAll() {
		log.info("UserboardServiceImpl {}", "userSelectAll");
		return dao.userSelectAll();
	}

	@Override
	public List<UserVo> getAllUser() {
		log.info("UserboardServiceImpl {}", "getAllUser");
		return dao.getAllUser();
	}

	@Override
	public List<UserVo> getSearcherUser(Map<String, Object> map) {
		log.info("UserboardServiceImpl {}", "getSearcherUser");
		return dao.getSearcherUser(map);
	}

	@Override
	public String findId(Map<String, Object> map) {
		log.info("UserboardServiceImpl {}", "findId");
		return dao.findId(map);
	}

	@Override
	public int setChangeAuth(Map<String, Object> map) {
		log.info("UserboardServiceImpl {}", "setChangeAuth");
		return dao.setChangeAuth(map);
	}

}
