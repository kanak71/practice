package com.min.edu.ctrl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

import com.min.edu.entity.User;
import com.min.edu.repository.UserRepository;
import com.min.edu.security.Custom_OAuth2_User;

import org.springframework.ui.Model;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
public class HelloController {

	private final UserRepository userRepository;
	
	@GetMapping("/")
	public String home() {
		log.info("처음 요청 HelloController GET");
		return "index";
	}
	
	@GetMapping("/infoUser")
	public String userInfo(@AuthenticationPrincipal Custom_OAuth2_User oAuth2User,
							Model model, HttpSession session) {
		if(oAuth2User == null) {
			return "redirect:/";	//로그인이 안 되어 있다면 홈으로 이동
		}
		
		String email = oAuth2User.getUsername();	//이메일
		User user = userRepository.findByEmail(email)
									.orElse(null);
		System.out.println(email + "로 가입된 로그인 정보 :" + user);
		
		if(user != null) {
			model.addAttribute("user", user);
		}
		return "infoUser";
		
	}
	
	//소설 로그인 API 연결 끊기
	//**** 네이버 연결을 해제(네이버 Token Revocation(토큰 폐기)는 Spring Security가 지원하지 않음
	
	@Autowired
	private OAuth2AuthorizedClientService authorizedClientService;
	
	@GetMapping("/disconnect")
	public String disconnect(HttpServletRequest request,
							OAuth2AuthenticationToken authenticationToken) {
		//1) 인증된 사용자의 정보를 가져오기
		OAuth2AuthorizedClient client =  authorizedClientService.loadAuthorizedClient(
				authenticationToken.getAuthorizedClientRegistrationId(),
				authenticationToken.getName()
				);
		//2) 액세스 토큰 가져오기
		String accessToken = client.getAccessToken().getTokenValue();
		
		//3) 네이버 연결 해제 API호출
		disconnectNaver(accessToken);
		
		//4)사용자 정보를 삭제(DB에서) => 실무 혹은 프로젝트에서는 사용불가로 변경 update
		String email = authenticationToken.getPrincipal().getAttribute("email");
		userRepository.findByEmail(email)
						.ifPresent(user -> userRepository.delete(user));
		
		SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();
		logoutHandler.logout(request, null, null);
		
		return "index";
	}
	
	//요청을 보내는 URL에 맞춰서 주소를 작성하고 naver로 요청을 보낸ㅁ
	//필요한 필수값은 API에 문서에 따라서 준비
	
	
	@Value("${spring.security.oauth2.client.registration.naver.client-id}")
	private String clientId;
	//2 Client_secret
	@Value("${spring.security.oauth2.client.registration.naver.client-secret}")
	private String clientSecret;
	@Value("${spring.security.oauth2.client.provider.naver.token-uri}")
	private String naverUnlinkUrl;
	
	private void disconnectNaver(String accessToken) {
		//서버에서 화면 없이 요청을 보내 RestTemplate 객체를 사용하면 된다
		RestTemplate restTemplate = new RestTemplate();
		
		//Naver API 요청에 맞는 API 작성
		String url = naverUnlinkUrl + 
				"?grant_type=delete"+
				"&client_id=" + clientId + 
				"&client_secret=" + clientSecret+
				"&access_token="+accessToken+
				"&service_provider=NAVER";
		//외부 API(naver서버)에 HTTP 요청 보내기
		//Header 정보(메타정보)
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);//x-www-form-urlencoded
		
		HttpEntity<String> entity = new HttpEntity<String>(headers);
		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
		
		response.getStatusCode();
		response.getBody();
		response.getHeaders();
		System.out.println("네이버 연결 해제 응답결과 : " + response);
				
		
	}
	
		
}
