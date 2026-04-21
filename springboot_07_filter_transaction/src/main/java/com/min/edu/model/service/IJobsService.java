package com.min.edu.model.service;

import java.util.List;
import java.util.Map;

import com.min.edu.vo.JobsVo;

//TODO 007 DAO를 실행시키고, Transcation 처를 위한 Interface Service
public interface IJobsService {
	
	//TODO 008 dao를 조합하여 실행히주는 interface
	public List<JobsVo> allSelectJob();
	public int insertJob(Map<String, Object> map);
	public int updateJob();
	
	//TODO 020 Transcation 처리를 위한 Service Interface
	//	insert, update를 묶어서 Transaction 처리를 해보겠다
	public int transaction(Map<String, Object> map) throws Exception;
}
