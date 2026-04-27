package com.min.edu.ctrl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;

//TODO 202메일 Controller
@Controller
@Slf4j
public class MailController {
	
	//TODO 203 springboot는 application.properties의 작성을 통해서 자동으로 객체를 생성한다
	//		JavaMailSender을 주입해서 사용하면 된다
	
	@Autowired
	private JavaMailSender mailSender;
	
	//TODO 204 메일 작성 화면 이동
	@GetMapping(value ="/mailForm.do")
	public String mailForm() {
		log.info("MailController mailForm.do 메일 작성 화면 이동");
		return "mailForm";
	}
	
	//TODO 206 메일 작성 입력 및 메일 보내기 요청
	//Spring에서는 Auto Binding을 사용하기 때문에
	// - 화면의 form 요소중 text에 관련된 것은 자동으로 Map이나 DTO에 담김
	// - 파일에 관련된 요소는 MultiPartFile에 자동으로 담김
	@PostMapping(value="/mailSender.do")
	public String mailSender(@RequestParam Map<String, String> mailMap,
								@RequestParam(required = false) MultipartFile attachFile) {
		
		log.info("MailController mailSender.do 요청값 : mailMap {}", mailMap);
		
		//1) 자신의 SMTP의 자신의 메일 주소를 필수로 입력되어야 한다
		String setFrom = "nanani715@gmail.com";
		
		//2) 메일 내용을 전송하기 위한 객체 - MimeMessage
		MimeMessage message = mailSender.createMimeMessage();
		
		try {
			//3) MimeMessageHelper : 송신서버 저장소, 파일첨부여부, 텍스트 인코딩 형식
			MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");	//파일이 첨부된 메일
			helper.setFrom(setFrom);
			helper.setTo(mailMap.get("email"));
			helper.setSubject(mailMap.get("title"));	//생략가능한데, 작성 안하면 스팸메일로 들어감
			helper.setEncodeFilenames(true);
			
			helper.setText(mailMap.get("content"), true);	//보내는 내용의 형태(true HTML, false text)
			
			//첨부파일(하드코딩)
			FileSystemResource fileSystemResource = new FileSystemResource("C:\\Program_Eclipse\\eclipse\\workspace_SpringBoot\\springboot_11_edu_book\\src\\main\\resources\\static\\1.png");
			helper.addAttachment("1.png", fileSystemResource);
			
			mailSender.send(message);
			
		} catch (MessagingException e) {	
			e.printStackTrace();
		}
		
		return "redirect:/mailForm.do";
		
	}

}












