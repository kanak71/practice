package com.min.edu.ctrl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

//TODO 303 파일 업로드 다운로드 Controller

@Controller
@Slf4j
public class FileController {

	//TODO 304 파일업로드 화면 이동
	@GetMapping(value = "/uploadForm.do")
	public String uploadForm() {
		log.info("FileController uploadForm.do GET 파일 업로드 화면 이동");
		return "uploadForm";
	}
	
	//TODO 305 파일 업로드 및 저장처리 MultipartFile
	@PostMapping(value = "/upload.do")
	public String fileUpload(
			@RequestParam("file") List<MultipartFile> files,	//클라이언트에서 보낸 파일 리스트(<input name="file">)
			String desc,										//함께 전달된 텍스트 설명
			HttpServletRequest request,							//서버 내 실제 경로를 계산하기 위한 객체
			Model model											//결과 데이터를(JSP/HTML)로 전달하는 객체
			) {
		//1) 업로드 요청 정보 확인
		log.info("업로드 파일 개수 : {}", files.size());
		log.info("설명(desc) : {}", desc);
		
		//2) 파일 정보 담을 리스트 초기화
		List<String> originFileNames = new ArrayList<String>();	//원본 파일명 저장
		List<String> saveFileNames = new ArrayList<String>();	//서버 저장용 파일명(UUID) 저장용
		
		//3) 저장 경로 설정
		/*
		 * getRealPath : 프로젝트 내 실제 물리적인 경로를 가져옴 - 장점 : 저장 경로를 배포하는 곳, 단점 : 서버가 꺼지면 파일이 초기화
		 * 					ㄴ 외부저장 경로를 사용하는게 일반적이다
		 * 
		 * - springboot에서는 대부분의 정보를 application.properties에 선언
		 *  @Value("${}")
		 */
		String path = request.getSession().getServletContext().getRealPath("/storage");
		
		//4) 사용하는 폴더가 존재하는지 알 수 없기 때문에 파일(File객체)를통해서 확인 후 필요시 생성
		File storage = new File(path);
		if(!storage.exists()) {
			storage.mkdirs();	//폴더가 없으면 상위 폴더까지 포함하여 생성
			log.info("폴더 생성 완료 : {}", path);
			
		}
		
		//5) 파일 리스트 순회(다중파일업로드)
		for (MultipartFile f : files) {
			//파일이 비어있는지 확인(선택하지 않고 전송했을때)
			if(f.isEmpty()) continue;
			
			//6) 파일명 변환(중복 방지)
			String originFileName = f.getOriginalFilename();	//사용자가 올린 파일의 원래 이름
			// 확장자 추출(예: pdf, png, jpg...)
			String extension = originFileName.substring(originFileName.lastIndexOf("."));
			//UUID를 통해서 파일명 중복 방지
			String saveFileName = UUID.randomUUID().toString()+extension;
			
			log.info("처리 중인 파일 : {} -> 원본 이름 \t {} -> 저장이름", originFileName, saveFileName);
			
			try {
				//7) 파일 저장 실행
				File targetFile = new File(path+File.separator+saveFileName);
				//transferTo() : 임시 저장된 파일을 실제 대상 경로로 이동(기존 Stream 복자 로직 대체)
				f.transferTo(targetFile);
				
				//8) 성공시 리스트에 파일명 추가
				originFileNames.add(originFileName);
				saveFileNames.add(saveFileName);
				
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		
		//9) 화면으로 전달 데이터
		model.addAttribute("originalFileNames", originFileNames);	//원본파일 리스트 : 사용자한테 보여줌
		model.addAttribute("saveFileNames", saveFileNames);			//저장된 파일 리스트 : 서버에서 찾을 때
		model.addAttribute("path", path);							//저장된 경로
		
		
		return "downloadFile";
		
	}
	
	//TODO 309 파일 다운로드 요청
	/*
	 * 1) header 정보를 html이나 String이 아닌 attachment로 보내주면, 브라우저가 다운로드 기능을 실행시켜준다
	 * 2) DispactherServlet이 페이지 요청이 아니라 값(byte[])을 보내줘야 한다 @ResponseBody로 준다
	 * 
	 */
	@PostMapping("/download.do")
	@ResponseBody
	public byte[] download(
				String originFileName, //파일을 찾거나 저장파일명
				String saveFileName,	//물리적인 파일을 찾을 때
				HttpServletRequest request,	//파일 경로 찾기
				HttpServletResponse response	//헤더 정보를 변경해서 파일 다운로드 기능을 요청
			) throws IOException {
		//1) 다운로드할 파일의 위치를 찾는다(DB를 사용하면 쉽다 - 식별자, 파일명+경로)
		String path = request.getSession().getServletContext().getRealPath("/storage");
		
		//2) 파일 위치에서 요청한 파일명을 읽어서 File 객체로 만든다
		File file = new File(path+"/"+saveFileName);
		
		//3) 파일명 인코딩
		String outputFileName = new String(originFileName.getBytes(),"8859_1");
		
		//4) 파일을 복제하여 byte[]로 읽어 줌
		byte[] bytes = FileCopyUtils.copyToByteArray(file);
		
		//5) 파일을 브라우저에 응답해주기
		response.setHeader("Content-Disposition", "attachment; filename=\""+outputFileName+"\"");
		response.setContentLength(bytes.length);
		response.setContentType("applicatoin/octet-stream");	//만약에 ms word보내요 - application/msword
		
		
		return bytes;
	}
	
}
















