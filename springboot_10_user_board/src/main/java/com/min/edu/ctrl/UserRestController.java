package com.min.edu.ctrl;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.min.edu.model.service.IUserService;
import com.min.edu.vo.UserVo;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

//TODO 029 RestController 사용자 비동기처리 컨트롤러
/*
 * 화면이 아닌 값만을 반환하는 컨트롤러
 * 기존 : @Controller에 해당 요청 메소드에 @ResponseBody 작성을 했다
 * 현재 : COntroller에 있는 모든 메소드를 @ResponseBody로 처리 하는 것 @RestController
 */

@RestController
@RequiredArgsConstructor
@Slf4j
public class UserRestController {
	
	private final IUserService service;
	
	//TODO 030 jQuery AJAX요청 => 결과는 JSON text로 반환한다
	//						=> 실행시킨 곳에서 dataType="json" => JSON text를 javascript JSON Object로 자동 변환
	@PostMapping("/duplicatejQuery.do")
	public String duplicatejQuery(String checkId) {
		log.info("UserRestController duplicatejQuery.do POST 아이디 중복 검사");
		
		int check = service.isDuplicateCheck(checkId);
		Map<String, String> map = new HashMap<String, String>();
		String chk = (check==0)? "true":"false";
		
		map.put("isc", chk);	//{"isc":"true"|"false"}
		
		//Map을 GSON 라이브러리를 통해서 JSON 모양으로 변경해 본다
		Gson gson = new GsonBuilder().create();
		String checkJson = gson.toJson(map);
		log.info("완료된 판단결과의 JSON to String : {}", checkJson);
		
		return checkJson;	//{"isc":"true"|"false"}
	}
	
	//TODO 033 fetch AJAX 요청
	@PostMapping(value="/duplicationFetch.do")
	public ResponseEntity<Map<String, String>> duplicationFetch(String checkId){
		log.info("UserRestController duplicationFetch.do POST Fetch AJAX");
		
		int check = service.isDuplicateCheck(checkId);
		String chk = (check == 1)? "false":"true";
		
		Map<String, String> result = new HashMap<String, String>();
		result.put("isc", chk);	//{"isc":"true"|"false}
		
		return ResponseEntity.ok(result);
	}
	
	//TODO 041 아이디 찾기 fetch Ajax 요청
	@PostMapping(value="/findId.do")
	public String findId(@RequestParam Map<String, Object> map) {
		log.info("UserRestController findId.do POST 아이디 찾기 : ", map);
		String id = service.findId(map);
		return StringUtils.defaultIfEmpty(id, "");
	}
	
	//TODO 073 회원검색 fetch Ajax 요청
	/*
	 * 1. SpringBoot에서는 JSON을 @ResponseBody 객체를 자동으로 JSON 모양으로 변경
	 * 2. Map {"id"="홍길동"} => return map => {"id":"홍길동"}
	 * 3. UserVo => toString() => {"id":"user02","name":"홍길동", "email":"sample@sample.com"}
	 * 4. lists<UserVo> => [
	 * 
	 * 						]
	 *  이렇게 java의 객체를 자동으로 JSON으로 변환해주는 라이브러리 => jackson-bind 라이브러리
	 *  												=> 동작이 되려면 starter spring web 있어야 한다
	 *  
	 */
	@PostMapping(value= "/getSearchUser.do")
	public List<UserVo> getSearchUser(@RequestParam Map<String, Object> map){
		log.info("UserRestController getSearchUser.do POST 회원검색 : {}", map);
		
		List<UserVo> lists = service.getSearcherUser(map);
		
		//1) JSON Object 작성방법
		
		
		//2) JSON Array 작성방법
		
		//3) Gson 사용 방법
		Gson gson = new GsonBuilder().create();	//Gson객체를 만드는 방법
		gson.toJson(lists);
		
		
		//4) SpringBoot에서 JCF는 자동으로 JSON 처리가 된다
		
		return lists;	//Spring 
	}
	
}







