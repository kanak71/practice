package com.min.edu.api;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.Instant;
import java.util.List;

import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.assertj.MockMvcTester.MockMvcRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.is;

import com.min.edu.domain.Bookmark;
import com.min.edu.repository.BookmarkRepository;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
//MVC 테스트를 위한 자동 설정
@AutoConfigureMockMvc

//테스트 컨테이너 추가
@TestPropertySource(properties = {
		"spring.datasource.url=jdbc:tc:postgresql:14-alpine:///demo"
})


class BookmarkControllerTest {
	
	@Autowired
	private MockMvc mvc;
	
	@Autowired
	private BookmarkRepository bookmarkRepository;
	
	@BeforeEach
	void setUp() {
		bookmarkRepository.deleteAllInBatch();
		List<Bookmark> bookmarks = List.of(
		        new Bookmark(null, "OpenAI", "https://openai.com", Instant.now()),
		        new Bookmark(null, "Notion", "https://www.notion.so", Instant.now()),
		        new Bookmark(null, "Figma", "https://www.figma.com", Instant.now()),
		        new Bookmark(null, "Slack", "https://slack.com", Instant.now()),
		        new Bookmark(null, "Discord", "https://discord.com", Instant.now()),
		        new Bookmark(null, "Spotify", "https://www.spotify.com", Instant.now()),
		        new Bookmark(null, "Netflix", "https://www.netflix.com", Instant.now()),
		        new Bookmark(null, "Airbnb", "https://www.airbnb.com", Instant.now()),
		        new Bookmark(null, "LinkedIn", "https://www.linkedin.com", Instant.now()),
		        new Bookmark(null, "Kakao", "https://www.kakao.com", Instant.now()),
		        new Bookmark(null, "Coupang", "https://www.coupang.com", Instant.now()),
		        new Bookmark(null, "Toss", "https://toss.im", Instant.now()),
		        new Bookmark(null, "Naver", "https://www.naver.com", Instant.now()),
		        new Bookmark(null, "Baemin", "https://www.baemin.com", Instant.now()),
		        new Bookmark(null, "Samsung", "https://www.samsung.com", Instant.now())
		);

		bookmarkRepository.saveAll(bookmarks);
	}
	

	//@Test
	void shouldBookmarks() throws Exception {
		//1번 테스트 단순히 /api/bookmarks의 호출 여부
		mvc.perform(MockMvcRequestBuilders.get("/api/bookmarks"))
			.andExpect(status().isOk())
			
		//2번 테스트 @BeforeEach에 의해서 모두 삭제되었기 때문에 totalElement가 0이면 된다
		//	.andExpect(jsonPath("$.totalElements", CoreMatchers.equalTo(0)));
			
		//3번
			.andExpect(jsonPath("$.totalElements", CoreMatchers.equalTo(15)))
			.andExpect(jsonPath("$.totalPages", CoreMatchers.equalTo(2)))
			.andExpect(jsonPath("$.currentPage", CoreMatchers.equalTo(1)))
			.andExpect(jsonPath("$.isFirst", CoreMatchers.equalTo(true)))
			.andExpect(jsonPath("$.isLast", CoreMatchers.equalTo(false)))
			.andExpect(jsonPath("$.hasNext", CoreMatchers.equalTo(true)))
			.andExpect(jsonPath("$.hasPrevious", CoreMatchers.equalTo(false)));
			
			
	}
	
	//페이징을 값을 입력해서 나오는 결과를 테스트란다
	@ParameterizedTest
	@CsvSource({"1,15,2,1,true,false,true,false", "2,15,2,2,false,true,false,true"})
	void shouldBookmarksPage(
			int pageNo, int totalElements, int totalPages, int currentPage,
			boolean isFirst, boolean isLast, boolean hasNext, boolean hasPrevious
			) throws Exception {
		
		mvc.perform(MockMvcRequestBuilders.get("/api/bookmarks?page="+pageNo))
			.andExpect(jsonPath("$.totalElements", CoreMatchers.equalTo(totalElements)))
			.andExpect(jsonPath("$.totalPages", CoreMatchers.equalTo(totalPages)))
			.andExpect(jsonPath("$.currentPage", CoreMatchers.equalTo(currentPage)))
			.andExpect(jsonPath("$.isFirst", CoreMatchers.equalTo(isFirst)))
			.andExpect(jsonPath("$.isLast", CoreMatchers.equalTo(isLast)))
			.andExpect(jsonPath("$.hasNext", CoreMatchers.equalTo(hasNext)))
			.andExpect(jsonPath("$.hasPrevious", CoreMatchers.equalTo(hasPrevious)));
		
	}
	
	@Test
	public void shouldCreateBookmark() throws Exception{
		
		MvcResult result = mvc.perform(
				MockMvcRequestBuilders.post("/api/bookmarks")
				.contentType(MediaType.APPLICATION_JSON)
				.content("""
						{
							"title":"Like Lion Study"
						}
						
						""")
				)
				.andExpect(status().is4xxClientError())
				.andExpect(jsonPath("$.status", is(400)))
				.andExpect(jsonPath("$.field", is("url")))
				.andExpect(jsonPath("$.message", is("URL은 필수 입력값 입니다")))
				.andReturn();
				
		
		String contentType = result.getResponse().getContentType();
		System.out.println("Content-Type : \t" + contentType);
		
		String responseBody = result.getResponse().getContentAsString();
		System.out.println("Response JSON : \t " + responseBody);
		
	}

}












