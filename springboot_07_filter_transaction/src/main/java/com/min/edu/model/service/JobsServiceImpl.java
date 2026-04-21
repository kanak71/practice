package com.min.edu.model.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.min.edu.model.mapper.IJobsMapper;
import com.min.edu.vo.JobsVo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

//TODO 009 Service 기능 구현
//		Spring MVC 요청 		-> 처리		 -> DAO
//				@Controller -> @Service  -> @Repository		=> 구조를 OCP => Spring web starter
//		stereotype : @Component, @Controller, @service, @Repository => 자동으로 Bean이 되어 진다
@Service
@Slf4j
@RequiredArgsConstructor
public class JobsServiceImpl implements IJobsService {
	
	//DAO(IJobsMapper)를 주입하여 사용 : 생성자
	private final IJobsMapper dao;

	@Override
	public List<JobsVo> allSelectJob() {
		log.info("JobsServiceImpl : josb 테이블 전체조회 allSelectJob");
		return dao.allSelectJob();
	}

	@Override
	public int insertJob(Map<String, Object> map) {
		log.info("JobsServiceImpl : jobs 테이블 입력 insertJob");
		return dao.insertJob(map);
	}

	@Override
	public int updateJob() {
		log.info("JobsServiceImpl : jobs 테이블 수정 updateJob");
		return dao.updateJob();
	}
	
	//TODO 021 Transcation을 위한 Servlet 작성
	/*
	 * 이미 트랜잭션이 있다면 참여하고, 없으면 새로 만들어라(기본값)
	 * Exception 까지 포함해서 예외가 나면 무조건 롤백
	 * 
	 * 예외를 발생시키면 되는구나
	 *  ㄴ 중요내용 : SQL문을 오류 발생하면 oracle이 오류발생시켜준다
	 *  			하지만 Spring Container에게 오류를 알려줘야 한다(throws를 통해서 예외를 발생시킨다)
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public int transaction(Map<String, Object> map) throws Exception {
		log.info("JobsServiceImpl : JOBS 테이블 트렌잭션");
		int n = dao.updateJob();
		if(n == 0) {
			throw new Exception("update 실패");
		}
		int m = dao.insertJob(map);
		if(m == 0) {
			throw new Exception("insert 실패");
		}
		return 1;
	}

}








