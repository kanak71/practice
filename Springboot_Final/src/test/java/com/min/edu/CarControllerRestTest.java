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

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.MediaType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.restdocs.test.autoconfigure.AutoConfigureRestDocs;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import tools.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
class CarControllerRestTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Test
	@DisplayName("Spring Data REST 실행")
	void docs_test() throws Exception {
		this.mockMvc
			.perform(get("/api/vehicles").accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andDo(document("vehicles"))
			.andDo(print());
	}
	
	@ParameterizedTest
	@ValueSource(strings = {"2023"})
	@DisplayName("자동차 생산년도 조회")
	public void testGetCarByProductYear(String productYear) throws Exception {
		mockMvc.perform(get("/api/vehicles/search/findByProductYear?productYear="+productYear))
    	.andExpect(status().isOk())
    	.andDo(document("findByProductYear",
    			queryParameters(
    					parameterWithName("productYear").description("생산년도")),
    			responseFields(
    					fieldWithPath("_embedded.cars[].brand").description("차의 브랜드"),
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
	@ParameterizedTest
	@ValueSource(strings = {"103"})
	@DisplayName("자동차의 소유자 정보 검색")
	public void testGetOwnerFromCar(String owner) throws Exception {
		//1) 요청
		mockMvc.perform(get("/api/vehicles/"+owner+"/owner"))
		//2) 처리결과에 따라서 문서 : 200 status.isOk일때 문서를 만들겠다
			.andExpect(status().isOk())
		//3) 만들어질 문서의 이름
			.andDo(document("findByOwner",
		//4) 성공해서 반환될 JSON을 분석해서 문서로 작성될 필드 작성
					responseFields(
							fieldWithPath("ownerid").description("소유자의 아이디"),
							fieldWithPath("firstname").description("소유자의 성"),
							fieldWithPath("lastname").description("소유자의 이름"),
							fieldWithPath("_links.self.href").description("소유자 정보 링크"),
							fieldWithPath("_links.owner.href").description("소유자 자체 링크"),
							fieldWithPath("_links.cars.href").description("소유자 소유한 자동차의 목록 링크")
							)));
	}
	//java의 객체를 JSON으로 변경하기 위한 주입 jackson-bind 라이브러리 주입
	@Autowired
	private ObjectMapper objectMapper;
	
	@Test
	@DisplayName("새로운 자동차 입력")
	public void testCreateVehicle() throws Exception{
		Map<String, Object>vehicle = new HashMap<String, Object>();
		
		vehicle.put("brand", "Samsung");
		vehicle.put("model", "SM6");
		vehicle.put("color", "Black");
		vehicle.put("registerNumber", "BBV-1354");
		vehicle.put("productYear", "2027");
		vehicle.put("price", 4000000);
		vehicle.put("owner", 1);
		
		mockMvc.perform(post("http://localhost:8080/api/vehicles").accept(MediaType.APPLICATION_JSON).content(ObjectMapper.writeValueAsString(vehicle)))
			.andExpect(status().isCreated())
	}
	
	@ParameterizedTest
	@ValueSource( strings =  {"104"})
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









