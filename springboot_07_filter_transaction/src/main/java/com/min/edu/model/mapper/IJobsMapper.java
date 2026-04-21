package com.min.edu.model.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.min.edu.vo.JobsVo;

//TODO 005 myBatis를 실행시키기 위한 interface @Mapper

@Mapper
public interface IJobsMapper {

	public List<JobsVo> allSelectJob();
	public int insertJob(Map<String, Object> map);
	public int updateJob();
	
}
