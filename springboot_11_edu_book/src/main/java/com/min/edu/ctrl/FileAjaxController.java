package com.min.edu.ctrl;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

//TODO 402 AJAX REST Controller
@RestController
@Slf4j
public class FileAjaxController {

	//TODO 403 AJAX를 통한 다중 파일 업로드
	@PostMapping("/uploadAjax.do")
	public Map<String, String> fileUploadAjax(
				HttpServletRequest request,	//저장 경로 확인
				@RequestParam("file") List<MultipartFile> files,	//Fetch FormData의 key
				@RequestParam("desc") String desc	//FormData에 있는 글
			){
		
		log.info("파일 업로드 시작 : 개수:{} \t 내용:{}", files.size(), desc);
		
		//1) 저장경로
		String path = request.getSession().getServletContext().getRealPath("/storage");
		File storage = new File(path);
		
		//2) 폴더 판단
		if(!storage.exists()) {
			storage.mkdirs();
		}
		
		//3) 파일 처리(반복문)
		for(MultipartFile f : files) {
			if(f.isEmpty()) {
				continue;
			}
			
			//원본 -> 저장이름 생성
			String originName = f.getOriginalFilename();
			//UUID에서 사용할 확장자명 추출
			String ext = originName.substring(originName.lastIndexOf("."));
			//저장 파일명 작성
			String saveName = UUID.randomUUID().toString()+ext;
			
			//핵심 : Stream 없이 바로 저장 -> transferTo
			
			try {
				File targetFile = new File(path+File.separator+saveName);	//새로 만들어진 위치+파일명+확장자
				f.transferTo(targetFile);
			} catch (IOException e) {
				log.info("파일 저장 실패: {}", originName);
				return Collections.singletonMap("isc", "false");	//실패 시 Map을 즉시 반환
			}
		}
		
		Map<String, String> result = new HashMap<String, String>();
		result.put("isc", "true");
		
		return result;
		
	}
}
