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
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import java.util.HashMap;
import java.util.Map;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.restdocs.test.autoconfigure.AutoConfigureRestDocs;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import tools.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs(outputDir = "target/generated-snippets")
class CarControllerRestTest {

	@Autowired
	private MockMvc mockMvc;
	
//	@Test
	@DisplayName("Spring Data REST 실행")
	void docs_test() throws Exception {
		this.mockMvc
				.perform(get("/api/vehicles").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andDo(document("vehicles"))
				.andDo(print());
	}
	
//	@ParameterizedTest // parameter를 사용한다면 @Test가 아니라 @ParameterizedTest로 선언
	@ValueSource(strings = {"2023"})
	@DisplayName("자동차 생산년도 조회")
	public void testGetCarByProductYear(String productYear) throws Exception {
	 	mockMvc.perform(get("/api/vehicles/search/findByProductYear?productYear="+productYear)) // perform 요청 
    	.andExpect(status().isOk()) // andExpect 요청의 결과 성공(200)
    	.andDo(document("findByProductYear", // andDo 문서의 이름 (document("findByProductYear")
    			queryParameters( // queryParameters GET방식의 Query String
    					parameterWithName("productYear").description("생산년도")), //전송되는 Parameter를 문서로 사용할 이름과 설명
    			responseFields( // responseFields 요청을 보내고 난 후에 JSON 반환된 결과를 나타내는 곳
    					fieldWithPath("_embedded.cars[].brand").description("차의 브랜드"), // fieldWithPath 결과를 JSON 값을 뜯어서 이름과 설명
    					fieldWithPath("_embedded.cars[].id").description("차의 아이디"),
    					fieldWithPath("_embedded.cars[].model").description("차의 모델"),
    					fieldWithPath("_embedded.cars[].color").description("차의 색상"),
    					fieldWithPath("_embedded.cars[].productYear").description("차의 생산 연도"),
    					fieldWithPath("_embedded.cars[].price").description("차의 가격"),
    					fieldWithPath("_embedded.cars[].registerNumber").description("차의 등록 번호"),
    					fieldWithPath("_embedded.cars[]._links.self.href").description("차의 자체 링크"),
    					fieldWithPath("_embedded.cars[]._links.car.href").description("차의 상세 링크"),
    					fieldWithPath("_embedded.cars[]._links.owner.href").description("차의 소유자 링크"),
    					fieldWithPath("_links.self.href").description("현재 요청 링크")
    					)));
	}

	//952 자동차 소유자 조회
//	@ParameterizedTest
	@ValueSource(strings = {"952"})
	@DisplayName("자동차의 소유자 정보 검색")
	public void testGetOwnerFromCar(String owner) throws Exception {
		// 1)요청
		mockMvc.perform(get("http://localhost:8080/api/vehicles/"+owner+"/owner"))
		// 2) 처리결과에 따라서 문서 : 200 status.isOk 일때 문서를 만들겠다
			.andExpect(status().isOk())
		// 3) 만들어질 문서의 이름 
			.andDo(document("findByOwner",
		// 4) 성공해서 반활될 JSON을 분석해서 문서로 작성될 필드 작성
					responseFields(
							fieldWithPath("ownerid").description("소유자의 아이디"),
							fieldWithPath("firstname").description("소유자의 성"),
							fieldWithPath("lastname").description("소유자의 이름"),
							fieldWithPath("_links.self.href").description("소유자 정보 링크"),
							fieldWithPath("_links.owner.href").description("소유자 자체 링크"),
							fieldWithPath("_links.cars.href").description("소유자 소유한 자동차의 목록 링크")
					)));
	}
	
	
	// java의 객체를 JSON으로 변경하기 위한 주입 (jackson-bind 라이브러리 주입)
	@Autowired
	private ObjectMapper objectMapper;
	
//	@Test
	@DisplayName("새로운 자동차 입력")
	public void testCreateVehicle() throws Exception{
		Map<String, Object> vehicle = new HashMap<String, Object>();
		
		vehicle.put("brand", "Samsung");
		vehicle.put("model", "SM6");
		vehicle.put("color", "Black");
		vehicle.put("registerNumber", "BBV-1354");
		vehicle.put("productYear", "2027");
		vehicle.put("price", 4000000);
//		vehicle.put("owner", 1);
		
//		Map<String, Object> owner = new HashMap<String, Object>();
//		owner.put("ownerid", 1);
//		vehicle.put("owner", owner);
		
		vehicle.put("owner", "http://localhost:8080/api/owners/1");
		
		
		// accept : 입력할 객체의 타입 , content : 입력한 객체  => MediaType.APPLICATION_JSON : JSON 으로 처리 해야 한다
		mockMvc.perform(post("/api/vehicles").accept(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(vehicle)))
				// 201 Created 생성하고 성공하면 201로 반환
				.andExpect(status().isCreated())
				.andDo(document("create-vehicle",
						requestFields(
								fieldWithPath("brand").description("자동차의 브렌드"),
								fieldWithPath("model").description("자동차의 모델"),
								fieldWithPath("color").description("자동차의 색상"),
								fieldWithPath("registerNumber").description("자동차의 등록 번호"),
								fieldWithPath("productYear").description("자동차의 생산년도"),
								fieldWithPath("price").description("자동차의 가격"),
								fieldWithPath("owner").description("소유자의 아이디")
								)
						,responseFields(
								fieldWithPath("id").description("자동차의 아이디"),
								fieldWithPath("brand").description("자동차의 브렌드"),
								fieldWithPath("model").description("자동차의 모델"),
								fieldWithPath("color").description("자동차의 색상"),
								fieldWithPath("registerNumber").description("자동차의 등록 번호"),
								fieldWithPath("productYear").description("자동차의 생산년도"),
								fieldWithPath("price").description("자동차의 가격"),
								fieldWithPath("_links").description("옵션 필드").optional(), // 이 필드는 없는 경우 문서화 되지 않는다
								fieldWithPath("_links.self.href").description("소유자 정보 링크"),
								fieldWithPath("_links.owner.href").description("소유자 자체 링크"),
								fieldWithPath("_links.car.href").description("소유자가 소유한 자동차 목록 링크")
								)));
		
	}
	
//	@Test
	@DisplayName("자동자 소유자 변경(JSON PATCH)")
	public void testVehicleToOwner() throws Exception{
		
		Map<String, Object> owner = new HashMap<String, Object>();
		owner.put("owner", "http://localhost:8080/api/owners/2");
		
		mockMvc.perform(patch("http://localhost:8080/api/vehicles/1102", 1102)
		        .contentType(MediaType.APPLICATION_JSON)
		        .content(objectMapper.writeValueAsString(owner)))
		    .andExpect(status().isNoContent()) // 204 성공하고 입력 반환타입이 없는 형태 PATCH 
		    .andDo(print()) // console에  출력
		    .andDo(document("change-vehicle-owner"));
	}
	
	
	@ParameterizedTest
	@ValueSource( strings =  {"105"})
	@DisplayName("자동차 ID를 통한 삭제")
	public void deleteVehicle(String delCarId) throws Exception {
		mockMvc.perform(delete("http://localhost:8080/api/vehicles/{id}", delCarId))
			.andExpect(status().isNoContent())
			.andDo(document("delete-vehicle",
					pathParameters(
							parameterWithName("id").description("삭제할 자동차의 아이디")
							)
					));
	}
	
	
}












