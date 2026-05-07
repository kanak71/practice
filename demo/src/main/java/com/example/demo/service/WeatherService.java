package com.example.demo.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.demo.dto.WeatherResponseDto;

@Service
public class WeatherService {

	@Value("${weather.api.key}")
	private String apiKey;
	
	
	@Value("${weather.api.url}")
	private String apiUrl;
	
	private final RestTemplate restTemplate = new RestTemplate();
	
	public WeatherResponseDto getWeather(String city) {
		//https://api.openweathermap.org/data/2.5/weather?q=${city}&appid=${API_KEY}&units=metric&lang=kr
		
		String url = apiUrl+"q="+city+"&appid="+apiKey+"&units=metric&lang=kr";
		
		Map<String, Object> response = restTemplate.getForObject(url, Map.class);
		
		Map<String, Object> main =
                (Map<String, Object>) response.get("main");
        double temp = (double) main.get("temp");

		Map<String, Object> weather =
                (Map<String, Object>) ((java.util.List<?>) response.get("weather")).get(0);

        String description = (String) weather.get("description");
        String icon = (String) weather.get("icon");
        
        return new WeatherResponseDto(city, temp, description, icon);
        		
	}
}













