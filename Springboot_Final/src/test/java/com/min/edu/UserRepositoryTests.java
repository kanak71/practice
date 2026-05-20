package com.min.edu;

import static org.junit.jupiter.api.Assertions.*;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.patch;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.request.RequestDocumentation.queryParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.restdocs.test.autoconfigure.AutoConfigureRestDocs;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@SpringBootTest
@AutoConfigureMockMvc // Spring REST DOCs의 설정을 자동으로 구성, 문서화의 결과물을 생성하는 설정
@AutoConfigureRestDocs(outputDir = "target/generated-snippets")
class UserRepositoryTests {

	@Autowired
	private MockMvc mockMvc;
	
	@Test
	public void loginSessionTest() throws Exception{
		//application.properties 테스트를 위한 Security 의 계정 값을 고정
		Map<String, String> loginRequest = new HashMap<String, String>();
		loginRequest.put("username", "user");
		loginRequest.put("password", "usertest");
		
		// 요청(form-POST, x-www-form-urlencode로 전송) ☞ 결과 값을 받음(MvcResult) ☞ Session정보
		MvcResult result = mockMvc.perform(post("/login")
							.with(csrf())
							.contentType(MediaType.APPLICATION_FORM_URLENCODED)
							.content("username="+loginRequest.get("username")+"&password="+loginRequest.get("password"))
							.with(httpBasic(loginRequest.get("username"), loginRequest.get("password"))) //Basic Auth 사용하기	
				)
				.andExpect(status().is3xxRedirection())
				.andReturn();
		
		//리디렉션된 URL을 확인
		String redirectedUrl = result.getResponse().getHeader("Location");
		System.out.println("리디렉션된 주소 : "+ redirectedUrl);
		
		
		//세션에서 인증 정보를 확인 한다
		MockHttpSession session = (MockHttpSession)result.getRequest().getSession();
		SecurityContext securityContext = (SecurityContext)session.getAttribute("SPRING_SECURITY_CONTEXT");
		
		assertNotNull(securityContext);
		assertTrue(securityContext.getAuthentication() != null);
		assertEquals("user", securityContext.getAuthentication().getName());
		
		
		//Spring REST DOCs를 통해서 문서화 작업
		mockMvc.perform(get("/api")
						.contentType(MediaType.APPLICATION_JSON) // 요청 Body의 타입을 지정 : JSON을 보낼겁니다
						.session(session) // 로그인된 세견의 상태 정보를 MockMvc 흉내낸다
						.accept(MediaType.APPLICATION_JSON) // 클라이언트가 응답하는 타입 지정 : JSON으로 응답해 주세요
						.with(csrf())) // CSRF 토큰 추가
				.andExpect(status().isOk())
				.andDo(document("after_login_api",
						responseFields( // 응답 필드 문서화
							fieldWithPath("_links").description("리소스 링크"),
							fieldWithPath("_links.cars.href").description("자동차 정보 링크"),
							fieldWithPath("_links.cars.templated").description("자동차 정보 링크가 URI Template"),
							fieldWithPath("_links.owners.href").description("사용자 정보 링크"),
							fieldWithPath("_links.owners.templated").description("사용자 정보 링크가 URI Template"),
							fieldWithPath("_links.profile.href").description("프로파일 링크")
								)
						))
				.andDo(print());
	}
}


