package com.min.edu.model;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.min.edu.dto.EmpVo;

//TODO 011 mybatis 인터페이스 방식으로 작성
/*
 * 메소드명 = id
 * CRUD(쿼리)를 annotation 방법으로 사용
 * @Select, @Insert, @Update, @Delete를 선언하여 사용한다
 * Dynamic 쿼리는 text 형식으로 작성.
 * 	반환타입 = resultType
 * 	Arguments = parameterType
 *  
 */

@Mapper
public interface IEmpDaoInterface {
	//전체조회(쿼리를 text block(""")으로 작성하면 된다
	@Select("""
			SELECT e.EMPNO, e.ENAME, e.JOB ,
				e.MGR , e.HIREDATE , e.SAL ,
				e.COMM , e.DEPTNO 
			FROM EMP e 
			""")
	public List<EmpVo> selectBoardInterface();
	
	
	//TODO 013 interface Mapper에서 다이나믹쿼리, 바인딩(파라미터) 처리
	@Select("""
			<script>
			SELECT e.EMPNO, e.ENAME, e.JOB ,
				e.MGR , e.HIREDATE , e.SAL ,
				e.COMM , e.DEPTNO 
			FROM EMP e 
			WHERE 1=1
				<if test="sal != null">
					AND SAL &gt;= #{sal}
				</if>
			</script>
			""")
	public List<EmpVo> selectEmpBySal(@Param("sal") String sal);
	
}









